package refatorado.game.enemy;
import java.awt.Color;
import java.util.LinkedList;
import refatorado.game.Level;
import refatorado.game.Main;
import refatorado.game.lifebar.LifeBarEnemy;
import refatorado.game.projectile.Eprojectile;
import refatorado.gamelib.GameLib;

public class Overlord extends Enemy implements EnemyInterface {
	private long nextShot;
	int color, colorAux;
	
	public Overlord (double x, double y, long spawn, int maxHP){
		super(x, y, spawn);
		if (maxHP < 1) maxHP = 1;
		life = new LifeBarEnemy (maxHP, "OVERLORD");
		position.angle = 0.0; 		//3 * Math.PI
		speed.x = 0.20;
		speed.y = 0.30;
		RV = 0.0;
		nextShot = Level.getCurrentTime();
		radius = 60.0;
		
		if (x < radius) position.x = radius;
		else if (x > GameLib.WIDTH - radius) position.x = GameLib.WIDTH - radius;
		if (y < radius + 80) position.y = radius + 80;
		else if (y > GameLib.HEIGHT - radius - 80) position.y = GameLib.HEIGHT - radius - 80;
		
		color = 0;
		colorAux = 0;
		sb = new LaserShot();
		mb = new WaveMove(this, 80);
	}	
	
	public void draw(){
		for (Eprojectile projectile : projectiles)
			projectile.draw();
		if(!exploding){
			Color c = Color.GRAY;
			if (color > 254) colorAux = -1;
			else if (color < 80) colorAux = 1;
			color += colorAux;
			c = new Color (255, color, 120);
			GameLib.setColor(Color.GRAY);
			GameLib.drawBall(position.x, position.y, radius);
			GameLib.setColor(Color.BLACK);
			GameLib.drawBall(position.x, position.y, radius*0.7);
			GameLib.setColor(c);
			GameLib.drawBall(position.x, position.y, radius*0.35);
			GameLib.setColor(Color.BLACK);
			GameLib.fillRect(position.x, position.y + radius*0.8, radius*0.4, radius*0.4);
		} else if (Level.getCurrentTime() <= explosion_end){
			double alpha = (Level.getCurrentTime() - explosion_start) / (explosion_end - explosion_start);
			GameLib.drawExplosion(position.x, position.y, alpha);
		}
		life.draw();
	}
	
	public void update(){
		LinkedList <Eprojectile> inactiveProjectiles = new LinkedList <Eprojectile>();

		//atualizando os projeteis
		for (Eprojectile projectile : projectiles){
			if (projectile.getPositionY() > GameLib.HEIGHT){
				inactiveProjectiles.add(projectile);
			} else {
				projectile.update();
			}
		}
		
		for (Eprojectile projectile : inactiveProjectiles)
			projectiles.remove(projectile);
		
		//se o inimigo for explodido aquela posição do vetor passa a ser inativa
		if(!exploding){
			if (Level.getCurrentTime() > nextShot) {
				shoot();
				nextShot = (long) (Level.getCurrentTime() + 1000 + 1000 * Math.random());
			}
			move();
		}  else Main.EndLevel = true; //Se o boss tiver explodido
	}
	
}
