package refatorado.game;
import java.awt.Color;
import java.util.*;
import refatorado.game.lifebar.LifeBarPlayer;
import refatorado.game.projectile.Pprojectile;
import refatorado.gamelib.GameLib;

public class Player implements Observer, Character{
	List <Pprojectile> projectiles;
	LifeBarPlayer life;
	private Cordinate position;
	private Cordinate speed;
	private double radius;						// raio (tamanho aproximado do player)
	private double explosion_start;				// instante do in�cio da explos�o
	private double explosion_end;				// instante do final da explos�o
	private long nextShot;						// instante a partir do qual pode haver um pr�ximo tiro
	private boolean exploding;

	Player(int maxHP){
		if (maxHP < 1) maxHP = 1;
		life = new LifeBarPlayer (maxHP);
		projectiles = new ArrayList <Pprojectile>();
		position = new Cordinate(GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90);
		speed = new Cordinate (0.25, 0.25);
		radius = 12.0;
		explosion_start = 0;
		explosion_end = 0;
		nextShot = Level.getCurrentTime();
		exploding = false;
	}

	public double getPositionX() {
		return position.x;
	}
	
	public double getPositionY() {
		return position.y;
	}
	
	public double getRadius() {
		return radius;
	}

	public boolean isExploding() {
		return exploding;
	}
	
	public void setExploding() {
		exploding = true;
		explosion_start = Level.getCurrentTime();
		explosion_end = Level.getCurrentTime() + 2000;
	}

	private void verifyExplosion(){
		if(exploding){
			if(Level.getCurrentTime() > explosion_end){
				//state = Main.ACTIVE;
				exploding = false;
				life.restoreHp();
			}
		}
	}
	
	private void readIn(){
		/* Verificando entrada do usu�rio (teclado) */
		if(!exploding){
			if(GameLib.iskeyPressed(GameLib.KEY_UP)) position.y -= Level.getDelta() * speed.y;
			if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) position.y += Level.getDelta() * speed.y;
			if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) position.x -= Level.getDelta() * speed.x;
			if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) position.x += Level.getDelta() * speed.x;
			if(GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
				if(Level.getCurrentTime() > nextShot){
						projectiles.add(new Pprojectile(position.x, position.y - 2 * radius, 0.0, -1.0));
						nextShot = Level.getCurrentTime() + 100;
				}	
			}
		}
		
		if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) Main.setRunning(false);
		
		/* Verificando se coordenadas do player ainda est�o dentro	*/
		/* da tela de jogo ap�s processar entrada do usu�rio.       */
		if(position.x < 0.0) position.x = 0.0;
		if(position.x >= GameLib.WIDTH) position.x = GameLib.WIDTH - 1;
		if(position.y < 25.0) position.y = 25.0;
		if(position.y >= GameLib.HEIGHT) position.y = GameLib.HEIGHT - 1;
	}
	
	public boolean isVulnerable(){
		return life.isVulnerable();
	}
	
	public void draw(){
		for (Pprojectile projectile : projectiles)
			projectile.draw();
		
		/* desenhando player */
		if(exploding){
			double alpha = (Level.getCurrentTime() - explosion_start) / (explosion_end - explosion_start);
			GameLib.drawExplosion(position.x, position.y, alpha);
		}
		else {
			GameLib.setColor(Color.BLUE);
			GameLib.drawPlayer(position.x, position.y, radius);
		}
		
		life.draw();
	}
	
	public void update(){

		LinkedList <Pprojectile> inactiveProjectiles = new LinkedList <Pprojectile>();
		
		for (Pprojectile projectile : projectiles){
			if (projectile.getPositionY() < 0){
				inactiveProjectiles.add(projectile);
			} else {
				projectile.update();
			}
		}
		
		for (Pprojectile projectile : inactiveProjectiles)
			projectiles.remove(projectile);
		
		verifyExplosion();
		readIn();
	}

}


