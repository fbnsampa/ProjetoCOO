package refatorado.game.enemy;
import java.awt.Color;
import java.util.LinkedList;
import refatorado.game.Level;
import refatorado.game.Main;
import refatorado.game.lifebar.LifeBarEnemy;
import refatorado.game.projectile.Eprojectile;
import refatorado.gamelib.GameLib;

public class DeathStar extends Enemy implements EnemyInterface {
	private long nextCharge;
	private long nextShot;
	private int countShot;
	private boolean charging;
	private long chargingEnd;
	private double previousSpeedX;
	private double previousSpeedY;
	int color, colorAux;
	
	public DeathStar (){
		super();
		life = new LifeBarEnemy(10, "DEATHSTAR");
		position.x = 60;
		position.y = 80;
		position.angle = 0.0; 		//3 * Math.PI
		speed.x = 0.20;
		speed.y = 0.20;
		RV = 0.0;
		countShot = 0;
		nextShot = Level.getCurrentTime();
		nextCharge = nextShot + 4000;
		chargingEnd = nextShot;
		radius = 60.0;
		charging = false;
		color = 0;
		colorAux = 1;
		sb = new ExplosionShot();
		mb = new PongMove();
	}
	
	public DeathStar (double x, double y, long spawn, int maxHP){
		super(x, y, spawn);
		if (maxHP < 1) maxHP = 1;
		life = new LifeBarEnemy (maxHP, "DEATHSTAR");
		position.angle = 0.0; 		//3 * Math.PI
		speed.x = 0.20;
		speed.y = 0.20;
		previousSpeedX = 0.0;
		previousSpeedY = 0.0;
		RV = 0.0;
		nextShot = Level.getCurrentTime();
		nextCharge = nextShot + 4000;
		chargingEnd = nextShot;
		radius = 35.0;
		if (x < radius) position.x = radius;
		else if (x > GameLib.WIDTH - radius) position.x = GameLib.WIDTH - radius;
		if (y < radius) position.y = radius;
		else if (y > GameLib.HEIGHT - radius) position.y = GameLib.HEIGHT - radius;
		color = 0;
		colorAux = 0;
		sb = new ExplosionShot();
		mb = new PongMove();
	}	
	
	public void draw(){
		for (Eprojectile projectile : projectiles)
			projectile.draw();
		
		life.draw();
		
		if(!exploding){
			Color c = Color.GRAY;
			if (charging){
				if (color > 230) colorAux = -1;
				else if (color < 80) colorAux = 1;
				color += colorAux;
				c = new Color (color, color, color);
			}
			GameLib.setColor(c);
			GameLib.drawBall(position.x, position.y, radius);
			GameLib.setColor(Color.DARK_GRAY);
			GameLib.drawBall(position.x+radius/3.0, position.y-radius/2.5, radius/3.5);
			GameLib.fillRect(position.x, position.y, radius*2, 1);
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
			if (!charging){
				if(Level.getCurrentTime() > nextCharge && insideThreshold()){
					charging = true;
					previousSpeedX = speed.x;
					previousSpeedY = speed.y;
					speed.x = 0;
					speed.y = 0;
					chargingEnd = Level.getCurrentTime() + 2500;
					nextShot = Level.getCurrentTime() + 1500;
				}
			} else { //if charging
				if (Level.getCurrentTime() > chargingEnd){
					charging = false;
					speed.x = previousSpeedX;
					speed.y = previousSpeedY;
					nextCharge = Level.getCurrentTime() + 5000;
				} else if (Level.getCurrentTime() > nextShot) {
					shoot();
					countShot++;
					if (countShot < 3) nextShot = (long) (Level.getCurrentTime() + 100);
					else {
						nextShot = (long) (Level.getCurrentTime() + 4000);
						countShot = 0;
					}
				}
			}
			move();
		} else Main.EndLevel = true; //Se o boss tiver explodido
		
	}
	
}
