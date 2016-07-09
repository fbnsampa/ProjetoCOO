package refatorado.game.enemy;
import java.awt.Color;
import java.util.LinkedList;
import refatorado.game.Level;
import refatorado.game.projectile.Eprojectile;
import refatorado.gamelib.GameLib;

public class Worm extends Enemy implements EnemyInterface {
	private static long next;			//não será necessario na versao final
	private static long spawnX;			// coordenada x do próximo inimigo tipo 2 a aparecer
	private static int count = 0;		// contagem de inimigos tipo 2 (usada na "formação de voo")

	public Worm(){
		super();
		sb = new SplitShot();
		mb = new SpiralMove();
//		
		position.x = spawnX;
		position.y = -10.0;
		position.angle = (3 * Math.PI) / 2;
		speed.x = 0.42;
		speed.y = 0.42;
		radius = 12.0;
		RV = 0.0;
		count++;
		if(count == 10){
			Worm.count = 0; //verificar se nao deve ser -1
		}
	}
	
	public Worm(double x, double y, long spawn){
		super(x, y, spawn);
		sb = new SplitShot();
		mb = new SpiralMove();

		position.x = x;
		position.y = y;
		position.angle = (3 * Math.PI) / 2;
		speed.x = 0.42;
		speed.y = 0.42;
		radius = 12.0;
		RV = 0.0;
		count++;
		if(count == 10){
			count = 0; //verificar se nao deve ser -1
		}
	}
	
	public static long getNext() {
		return next;
	}

	public static void setNext(long next) {
		Worm.next = next;
	}

	public boolean isOutOfScreen(){
		if(position.x < -10 || position.x > GameLib.WIDTH + 10 ) return true;
		return false;
	}
	
	//Verifica se é momento de atirar
	private boolean shootNow(){
		double x = position.x + speed.x * Math.cos(position.angle) * Level.getDelta();
		double y = position.y + speed.y * Math.sin(position.angle) * Level.getDelta() * (-1.0);
		double angle = position.angle + RV * Level.getDelta();
		double rv = RV;
		double threshold = GameLib.HEIGHT * 0.30;
		
		if (position.y < threshold && y >= threshold){
			if (x < GameLib.WIDTH / 2) rv = 0.003;
			else rv = -0.003;
		}
		
		if (rv > 0 && Math.abs(angle - 3 * Math.PI) < 0.05) return true;
		if (rv < 0 && Math.abs(angle) < 0.05) return true;
		
		return false;
	}
	
	public void update(){
		if (update){
			LinkedList <Eprojectile> inactiveProjectiles = new LinkedList <Eprojectile>();
			
			//atualizando os projeteis
			for (Eprojectile projectile : projectiles){
				if (projectile.getPositionY() < 0 || projectile.getPositionX() < 0 || projectile.getPositionX() > GameLib.WIDTH){
					inactiveProjectiles.add(projectile);
				} else {
					projectile.update();
				}
			}
			
			for (Eprojectile projectile : inactiveProjectiles)
				projectiles.remove(projectile);
			
			if(!exploding && !isOutOfScreen()) {
				//Dependendo das condições durante o movimento o shoot é lançado ou não
				if (shootNow()) shoot();
				move();
			}
			this.update = false;
		}
	}
	
	public void draw(){
		for (Eprojectile projectile : projectiles){
			projectile.draw();
		}
		if(!exploding){
			GameLib.setColor(Color.MAGENTA);
			GameLib.drawDiamond(position.x, position.y, radius);
		} else if (Level.getCurrentTime() <= explosion_end){
			double alpha = (Level.getCurrentTime() - explosion_start) / (explosion_end - explosion_start);
			GameLib.drawExplosion(position.x, position.y, alpha);
		}
	}

}
