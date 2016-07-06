package refatorado.game;
import java.awt.Color;
import java.util.LinkedList;

import refatorado.gamelib.GameLib;

class Ship extends Enemy implements EnemyInterface  {
	private static long next;
	private long nextShoot;			// instantes do próximo tiro
	
	public Ship (){
		super();
		position.x = Math.random() * (GameLib.WIDTH - 20.0) + 10.0;
		position.y = -10.0;
		position.angle = 3 * Math.PI / 2;
		V = 0.20 + Math.random() * 0.15;
		RV = 0.0;
		nextShoot = Level.getCurrentTime() + 500;
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
	
	public void atualiza(){
		
		LinkedList <Eprojectile> inactiveProjectiles = new LinkedList <Eprojectile>();
		
		//atualizando os projeteis
		for (Eprojectile projectile : projectiles){
			if (projectile.getPositionY() < 0){
				inactiveProjectiles.add(projectile);
			} else {
				projectile.atualiza();
			}
		}
		
		for (Eprojectile projectile : inactiveProjectiles)
			projectiles.remove(projectile);
		
		//se o inimigo for explodido aquela posição do vetor passa a ser inativa
		if(!exploding && !isOutOfScreen()){
			//se o inimigo estiver acima do personagem e o tempo atual for maior
			//que o tempo do próximo tiro acontece um disparo
			if(Level.getCurrentTime() > nextShoot && position.y < Main.player.getPositionY()){
				shoot();
				//o tempo do próximo disparo é atualizado
				nextShoot = (long) (Level.getCurrentTime() + 200 + Math.random() * 500);
			}
			move();
		}
	}
	

	
	public void desenha(){
		for (Eprojectile projectile : projectiles)
			projectile.desenha();
		
		if(!exploding){
			GameLib.setColor(Color.CYAN);
			GameLib.drawCircle(position.x, position.y, radius);
		} else if (Level.getCurrentTime() <= explosion_end){
			double alpha = (Level.getCurrentTime() - explosion_start) / (explosion_end - explosion_start);
			GameLib.drawExplosion(position.x, position.y, alpha);
		}
	}

}
