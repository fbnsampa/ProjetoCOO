package refatorado;
import java.awt.Color;
import java.util.LinkedList;

public class Ship extends Enemy implements EnemyInterface  {
	static long next;
	long nextShoot;			// instantes do próximo tiro
	
	public Ship (){
		super();
		position.x = Math.random() * (GameLib.WIDTH - 20.0) + 10.0;
		position.y = -10.0;
		position.angle = 3 * Math.PI / 2;
		V = 0.20 + Math.random() * 0.15;
		RV = 0.0;
		nextShoot = Level.currentTime + 500;
		radius = 9.0;
		next = Level.currentTime + 500;
	}
	
	public boolean isOutOfScreen(){
		if(position.y > GameLib.HEIGHT + 10) return true;
		return false;
	}
	
	public void move (){
		position.x += V * Math.cos(position.angle) * Level.delta;
		position.y += V * Math.sin(position.angle) * Level.delta * (-1.0);
		position.angle += RV * Level.delta;
	}
	
	public void shoot (){
		//se o inimigo estiver acima do personagem e o tempo atual for maior
		//que o tempo do próximo tiro acontece um disparo
		if(Level.currentTime > nextShoot && position.y < Main.player.position.y){
			double vx = Math.cos(position.angle) * 0.45;
			double vy = Math.sin(position.angle) * 0.45 * (-1.0);
			Eprojectile novo = new Eprojectile(position.x, position.y, vx, vy);
			projectiles.add(novo);
			//o tempo do próximo disparo é atualizado
			nextShoot = (long) (Level.currentTime + 200 + Math.random() * 500);
		}
	}
	
	public void atualiza(){
		
		LinkedList <Eprojectile> inactiveProjectiles = new LinkedList <Eprojectile>();
		
		//atualizando os projeteis
		for (Eprojectile projectile : projectiles){
			if (projectile.position.y < 0){
				inactiveProjectiles.add(projectile);
			} else {
				projectile.atualiza();
			}
		}
		
		for (Eprojectile projectile : inactiveProjectiles)
			projectiles.remove(projectile);
		
		//se o inimigo for explodido aquela posição do vetor passa a ser inativa
		if(exploding){
			if(Level.currentTime > explosion_end){
				exploding = false;
			}
		} else {
			if (!isOutOfScreen()){
				move();
				shoot();
			}
			
		}
	}
	

	
	public void desenha(){
		for (Eprojectile projectile : projectiles)
			projectile.desenha();
		
		if(exploding){
			double alpha = (Level.currentTime - explosion_start) / (explosion_end - explosion_start);
			GameLib.drawExplosion(position.x, position.y, alpha);
		} else {
			GameLib.setColor(Color.CYAN);
			GameLib.drawCircle(position.x, position.y, radius);
		}
	}

}
