package refatorado;
import java.awt.Color;
import java.util.LinkedList;


public class Worm extends Enemy implements EnemyInterface {
	private static long next;
	private static long spawnX;			// coordenada x do próximo inimigo tipo 2 a aparecer
	private static int count = 0;		// contagem de inimigos tipo 2 (usada na "formação de voo")

	public Worm(){
		super();
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
	
	public boolean move(){
		boolean shootNow = false;	//variável que autoriza o uso de projetéis
		double previousY = position.y;
		//caso o inimigo esteja ativo são feitos os calculos e atribuições
		//para determinar sua movimentação
		position.x += V * Math.cos(position.angle) * Level.getDelta();
		position.y += V * Math.sin(position.angle) * Level.getDelta() * (-1.0);
		position.angle += RV * Level.getDelta();
		
		double threshold = GameLib.HEIGHT * 0.30;
		//dependendo de onde o inimigo 2 estiver na tela a sua rotação
		//é modificada
		if(previousY < threshold && position.y >= threshold) {
			if(position.x < GameLib.WIDTH / 2) RV = 0.003;
			else RV = -0.003;
		}
		
		//aqui dependendo de algumas condições é ativada a variavél
		//shootNow que permite o disparo de projéteis pelo inimigo2
		if(RV > 0 && Math.abs(position.angle - 3 * Math.PI) < 0.05){
			RV = 0.0;
			position.angle = 3 * Math.PI;
			shootNow = true;
		}
		
		if(RV < 0 && Math.abs(position.angle) < 0.05){
			RV = 0.0;
			position.angle = 0.0;
			shootNow = true;
		}
		
		return shootNow;
	}
	
	public void shoot (){
		double [] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };
		for(int k = 0; k < 3; k++){
			double a = angles[k] + Math.random() * Math.PI/6 - Math.PI/12;
			double vx = Math.cos(a) * 0.30;
			double vy = Math.sin(a) * 0.30;
			Eprojectile novo  = new Eprojectile(position.x, position.y, vx, vy);
			projectiles.add(novo);
		}
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
			if (move())	shoot();
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
