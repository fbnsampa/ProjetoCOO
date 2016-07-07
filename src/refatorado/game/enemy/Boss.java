package refatorado.game.enemy;
import java.awt.Color;
import java.util.LinkedList;

import refatorado.game.Level;
import refatorado.game.Main;
import refatorado.game.projectile.Eprojectile;
import refatorado.gamelib.GameLib;

public class Boss extends Enemy implements EnemyInterface {
	private long nextShot;
	
	
	public Boss (){
		super();
		position.x = GameLib.WIDTH/2;
		position.y = -12.0;
		position.angle = 0.0;  //3 * Math.PI
		speed.x = 0.0;
		speed.y = 0.20;
		RV = 0.0;
		nextShot = Level.getCurrentTime() + 1400;
		radius = 25.0;
		sb = new ExplosionShot();
		mb = new StraightMove();
		System.out.println("novo boss criado");
	}	
	
	public void draw(){
		for (Eprojectile projectile : projectiles)
			projectile.draw();
		
		if(!exploding){
			GameLib.setColor(Color.GRAY);
			GameLib.drawBall(position.x, position.y, radius);
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
			if(Level.getCurrentTime() > nextShot){
				shoot();
				//o tempo do próximo disparo é atualizado
				nextShot = (long) (Level.getCurrentTime() + 5000);
			}
			move();
		}
	}
	
	
}
