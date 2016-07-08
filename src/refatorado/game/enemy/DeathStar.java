package refatorado.game.enemy;
import java.awt.Color;
import java.util.LinkedList;

import refatorado.game.Level;
import refatorado.game.lifebar.LifeBarEnemy;
import refatorado.game.projectile.Eprojectile;
import refatorado.gamelib.GameLib;

public class DeathStar extends Enemy implements EnemyInterface {
	private long nextShot;
	
	public DeathStar (){
		super();
		life = new LifeBarEnemy(10);
		position.x = 60;
		position.y = 80;
		position.angle = 0.0; 		//3 * Math.PI
		speed.x = 0.20;
		speed.y = 0.20;
		RV = 0.0;
		nextShot = Level.getCurrentTime() + 800;
		radius = 40.0;
		sb = new ExplosionShot();
		mb = new PongMove();
	}
	
	public DeathStar (double x, double y, long spawn, int maxHP){
		super(x, y, spawn);
		if (maxHP < 1) maxHP = 1;
		life = new LifeBarEnemy (maxHP);
		position.angle = 0.0; 		//3 * Math.PI
		speed.x = 0.20;
		speed.y = 0.20;
		RV = 0.0;
		nextShot = Level.getCurrentTime() + 800;
		radius = 40.0;
		if (x < radius) position.x = radius;
		else if (x > GameLib.WIDTH - radius) position.x = GameLib.WIDTH - radius;
		if (y < radius) position.y = radius;
		else if (y > GameLib.HEIGHT - radius) position.y = GameLib.HEIGHT - radius;
		
		sb = new ExplosionShot();
		mb = new PongMove();
	}	
	
	public void draw(){
		for (Eprojectile projectile : projectiles)
			projectile.draw();
		
		life.draw();
		
		if(!exploding){
			GameLib.setColor(Color.GRAY);
			GameLib.drawBall(position.x, position.y, radius);
			GameLib.setColor(Color.DARK_GRAY);
			GameLib.drawBall(position.x+radius/3.0, position.y-radius/2.5, radius/3.5);
			GameLib.fillRect(position.x, position.y, radius*2, 1);
			
//			GameLib.setColor(Color.RED);
//			GameLib.fillRect(0.0, 0.0, 100.0, 52.0);
			
		} else if (Level.getCurrentTime() <= explosion_end){
			double alpha = (Level.getCurrentTime() - explosion_start) / (explosion_end - explosion_start);
			GameLib.drawExplosion(position.x, position.y, alpha);
		}
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
			//se o inimigo estiver acima do personagem e o tempo atual for maior
			//que o tempo do próximo tiro acontece um disparo
			if(Level.getCurrentTime() > nextShot &&
				position.x > 120.0 && position.x < GameLib.WIDTH - 120.0 &&
				position.y > 240.0 && position.y < GameLib.HEIGHT - 240){
				shoot();
				//o tempo do próximo disparo é atualizado
				nextShot = (long) (Level.getCurrentTime() + 4000);
			}
			move();
		}
	}
	
}
