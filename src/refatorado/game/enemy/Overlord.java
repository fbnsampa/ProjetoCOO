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
	private boolean charging;
	int color, colorAux;
	
	public Overlord (){
		super();
		life = new LifeBarEnemy(10, "OVERLORD");
		position.x = 80;
		position.y = 200;
		position.angle = 0.0; 		//3 * Math.PI
		speed.x = 0.20;
		speed.y = 0.30;
		RV = 0.0;
		nextShot = Level.getCurrentTime();
		radius = 60.0;
		charging = false;
		color = 0;
		colorAux = 1;
		sb = new LaserShot();
		mb = new WaveMove(this, 80);
	}
	
	public Overlord (double x, double y, long spawn, int maxHP){
		super(x, y, spawn);
		if (maxHP < 1) maxHP = 1;
		life = new LifeBarEnemy (maxHP, "OVERLORD");
		position.angle = 0.0; 		//3 * Math.PI
		speed.x = 0.20;
		speed.y = 0.20;
		RV = 0.0;
		nextShot = Level.getCurrentTime();
		radius = 60.0;
		if (x < radius) position.x = radius;
		else if (x > GameLib.WIDTH - radius) position.x = GameLib.WIDTH - radius;
		if (y < radius) position.y = radius;
		else if (y > GameLib.HEIGHT - radius) position.y = GameLib.HEIGHT - radius;
		color = 0;
		colorAux = 0;
		sb = new ExplosionShot();
		mb = new WaveMove();
	}	
	
	public void draw(){
		for (Eprojectile projectile : projectiles)
			projectile.draw();
		
		life.draw();
		
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
	}

	public boolean insideThreshold(){
		double threshold = 0.35;
		double thresholdX = GameLib.WIDTH * threshold;
		double thresholdY = GameLib.HEIGHT * threshold;
		
		if (position.y > thresholdY && position.x < GameLib.HEIGHT - thresholdX &&
				position.y < GameLib.HEIGHT - thresholdY && position.x > thresholdX) return true;
		
		return false;
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
