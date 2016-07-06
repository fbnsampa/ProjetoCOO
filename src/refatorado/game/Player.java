package refatorado.game;
import java.awt.Color;
import java.util.*;
import refatorado.game.projectile.Pprojectile;
import refatorado.gamelib.GameLib;
//talvez essa classe não precise ser public só ship precisa dela
public class Player implements Observer{
	List <Pprojectile> projectiles;//para mexer quando for usar pacotes
	private Cordinate position;
	private Cordinate speedy;
	private double radius;						// raio (tamanho aproximado do player)
	private double explosion_start;				// instante do início da explosão
	private double explosion_end;				// instante do final da explosão
	private long nextShot;						// instante a partir do qual pode haver um próximo tiro
	private boolean exploding;

	Player(){
		//state = Main.ACTIVE;
		projectiles = new ArrayList <Pprojectile>();
		position = new Cordinate(GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90);
		speedy = new Cordinate (0.25, 0.25);
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

	void verificaExplosionPlayer(){
		if(exploding){
			if(Level.getCurrentTime() > explosion_end){
				//state = Main.ACTIVE;
				exploding = false;
			}
		}
	}
	
	void verificaEntradaPlayer(){
		/********************************************/
		/* Verificando entrada do usuário (teclado) */
		/********************************************/
		
		if(!exploding){
			if(GameLib.iskeyPressed(GameLib.KEY_UP)) position.y -= Level.getDelta() * speedy.y;
			if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) position.y += Level.getDelta() * speedy.y;
			if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) position.x -= Level.getDelta() * speedy.x;
			if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) position.x += Level.getDelta() * speedy.x;
			if(GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
				if(Level.getCurrentTime() > nextShot){
						projectiles.add(new Pprojectile(position.x, position.y - 2 * radius, 0.0, -1.0));
						nextShot = Level.getCurrentTime() + 100;
				}	
			}
		}
		
		if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) Main.setRunning(false);
		
		/* Verificando se coordenadas do player ainda estão dentro	*/
		/* da tela de jogo após processar entrada do usuário.       */
		
		if(position.x < 0.0) position.x = 0.0;
		if(position.x >= GameLib.WIDTH) position.x = GameLib.WIDTH - 1;
		if(position.y < 25.0) position.y = 25.0;
		if(position.y >= GameLib.HEIGHT) position.y = GameLib.HEIGHT - 1;
	}
	
	void desenha(){
		for (Pprojectile projectile : projectiles)
			projectile.desenha();
		
		/* desenhando player */
		if(exploding){
			
			double alpha = (Level.getCurrentTime() - explosion_start) / (explosion_end - explosion_start);
			GameLib.drawExplosion(position.x, position.y, alpha);
		}
		else{
			
			GameLib.setColor(Color.BLUE);
			GameLib.drawPlayer(position.x, position.y, radius);
		}
	}
	
	public void atualiza(){

		LinkedList <Pprojectile> inactiveProjectiles = new LinkedList <Pprojectile>();
		
		for (Pprojectile projectile : projectiles){
			if (projectile.getPositionY() < 0){
				inactiveProjectiles.add(projectile);
			} else {
				projectile.atualiza();
			}
		}
		
		for (Pprojectile projectile : inactiveProjectiles)
			projectiles.remove(projectile);
		
		verificaExplosionPlayer();
		verificaEntradaPlayer();
	}

}


