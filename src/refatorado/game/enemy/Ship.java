package refatorado.game.enemy;
import java.awt.Color;
import java.util.LinkedList;
import refatorado.game.Level;
import refatorado.game.Main;
import refatorado.game.projectile.Eprojectile;
import refatorado.gamelib.GameLib;

public class Ship extends Enemy implements EnemyInterface  {
	private static long next;
	private long nextShot;			// instantes do próximo tiro
	
	public Ship (){
		super();
		position.x = Math.random() * (GameLib.WIDTH - 20.0) + 10.0;
		position.y = -10.0;
		position.angle = 4.7123889803847;  //3 * Math.PI
		speed.x = 0.0;
		speed.y = 0.20 + Math.random() * 0.15;
		RV = 0.0;
		nextShot = Level.getCurrentTime() + 500;
		radius = 9.0;
		next = Level.getCurrentTime() + 500;
		sb = new StraightShot();
		mb = new StraightMove();
	}
	
	public Ship (double x, double y, long spawn){
		super(x, y, spawn);
		position.angle = 4.7123889803847;  //3 * Math.PI
		speed.x = 0.0;
		speed.y = 0.20 + Math.random() * 0.15;
		RV = 0.0;
		nextShot = Level.getCurrentTime() + 500;
		radius = 9.0;
		next = Level.getCurrentTime() + 500;
		sb = new StraightShot();
		mb = new StraightMove();
	}
	
	public static long getNext() {
		return next;
	}

	public static void setNext(long next) {
		Ship.next = next;
	}

	public boolean isOutOfScreen(){
		if(position.y > GameLib.HEIGHT + 10) return true;
		return false;
	}
	
	public void update(){
		if (update){
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
			if(!exploding && !isOutOfScreen()){
				//se o inimigo estiver acima do personagem e o tempo atual for maior
				//que o tempo do próximo tiro acontece um disparo
				if(Level.getCurrentTime() > nextShot && position.y < Main.player.getPositionY()){
					shoot();
					//o tempo do próximo disparo é atualizado
					nextShot = (long) (Level.getCurrentTime() + 200 + Math.random() * 500);
				}
				move();
			}
			this.update = false;
		}
	}
	

	
	public void draw(){
		for (Eprojectile projectile : projectiles)
			projectile.draw();
		
		if(!exploding){
			GameLib.setColor(Color.CYAN);
			GameLib.drawCircle(position.x, position.y, radius);
		} else if (Level.getCurrentTime() <= explosion_end){
			double alpha = (Level.getCurrentTime() - explosion_start) / (explosion_end - explosion_start);
			GameLib.drawExplosion(position.x, position.y, alpha);
		}
	}

}
