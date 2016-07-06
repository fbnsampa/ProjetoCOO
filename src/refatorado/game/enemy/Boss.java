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
		position.y = 20;
		position.angle = 4.7123889803847;  //3 * Math.PI
		speed.x = 0.0;
		speed.y = 0.2;
		RV = 0.0;
		nextShot = Level.getCurrentTime() + 500;
		radius = 9.0;
		sb = new StraightShot();
		mb = new StraightMove();
		
	}	
	
	public void draw(){
		for (Eprojectile projectile : projectiles)
			projectile.draw();
		
		if(!exploding){
			//System.out.println("x : "  +  position.x  + "    y : " + position.y);
			GameLib.setColor(Color.BLUE);
			GameLib.drawCircle(position.x, position.y, radius);
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
		
		//se o inimigo for explodido aquela posi��o do vetor passa a ser inativa
		if(!exploding){
			//se o inimigo estiver acima do personagem e o tempo atual for maior
			//que o tempo do pr�ximo tiro acontece um disparo
			if(Level.getCurrentTime() > nextShot && position.y < Main.player.getPositionY()){
				shoot();
				//o tempo do pr�ximo disparo � atualizado
				nextShot = (long) (Level.getCurrentTime() + 200 + Math.random() * 500);
			}
			move();
		}
		this.update = false;
	}
	
	
}