package refatorado.game;
import java.awt.Color;
import java.util.LinkedList;

import refatorado.gamelib.GameLib;

class Worm extends Enemy implements EnemyInterface {
	private static long next;
	private static long spawnX;			// coordenada x do próximo inimigo tipo 2 a aparecer
	private static int count = 0;		// contagem de inimigos tipo 2 (usada na "formação de voo")

	public Worm(){
		super();
		sb = new SplitShot();
		mb = new SpiralMove();
		
		if (count == 0){
			spawnX = (long) (GameLib.WIDTH * 0.20);
			count = 1;
			radius = 12.0;
		} else {
			position.x = spawnX;
			position.y = -10.0;
			position.angle = (3 * Math.PI) / 2;
			V = 0.42;
			radius = 12.0;
			RV = 0.0;
			count++;
			if(count < 10){
				Worm.next = Level.getCurrentTime() + 120;
			} else { //count == 10
				Worm.count = 0; //verificar se nao deve ser -1
				Worm.spawnX = (long) (Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8);
				Worm.next = (long) (Level.getCurrentTime() + 3000 + Math.random() * 3000);
			}
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
	public boolean shootNow(){
		double x = position.x + V * Math.cos(position.angle) * Level.getDelta();
		double y = position.y + V * Math.sin(position.angle) * Level.getDelta() * (-1.0);
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
	
	public void atualiza(){
		
		LinkedList <Eprojectile> inactiveProjectiles = new LinkedList <Eprojectile>();
		
		//atualizando os projeteis
		for (Eprojectile projectile : projectiles){
			if (projectile.getPositionY() < 0 || projectile.getPositionX() < 0 || projectile.getPositionX() > GameLib.WIDTH){
				inactiveProjectiles.add(projectile);
			} else {
				projectile.atualiza();
			}
		}
		
		for (Eprojectile projectile : inactiveProjectiles)
			projectiles.remove(projectile);
		
		if(!exploding && !isOutOfScreen()) {
			//Dependendo das condições durante o movimento o shoot é lançado ou não
			if (shootNow()) shoot();
			move();
		}
	}
	
	public void desenha(){
		for (Eprojectile projectile : projectiles){
			projectile.desenha();
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
