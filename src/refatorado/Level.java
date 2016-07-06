package refatorado;
import java.util.*;

public class Level extends Subject<Enemy>{

	private static long currentTime;
	private static long delta;
	private Player player;
	private LinkedList <Enemy> explodingEnemys;

	
	public Level (){
		super();
		explodingEnemys = new LinkedList<Enemy>();
		player = Main.player;
		currentTime = 0;
		delta = 0;
		Ship.setNext(System.currentTimeMillis() + 2000); //bizarro porem provisorio
		Worm.setNext(System.currentTimeMillis() + 7000); //bizarro porem provisorio
	}
	
	public static long getCurrentTime() {
		return currentTime;
	}

	public static long getDelta() {
		return delta;
	}

	public void load (String name){
		//Carrega as configurações no documento de texto
		
	}
	
	//Verifica se alguma colisao explodiu o player
	//Se for verdade, altera o estado de player e interrompe a execução
	public void verifyPlayerColision (){
		for (Enemy enemy : observers){
			double dx, dy, dist;
			dx = enemy.getPositionX() - player.getPositionX();
			dy = enemy.getPositionY() - player.getPositionY();
			dist = Math.sqrt(dx * dx + dy * dy);

			if (dist < (player.getRadius() + enemy.getRadius()) * 0.8){
				player.setExploding();
				return;
			}
			
			for (Eprojectile projectile : enemy.projectiles){
				dx = projectile.getPositionX() - player.getPositionX();
				dy = projectile.getPositionY() - player.getPositionY();
				dist = Math.sqrt(dx * dx + dy * dy);
				
				if (dist < (player.getRadius() + projectile.getRadius()) * 0.8){
					player.setExploding();
					return;
				}
			}
		}
	}

	//Verifica se os projéteis atirados pelo player destruiu algum inimigo
	//Retorna uma lista ligada com todos os inimigos destruidos
	public void verifyEnemyColision (){
		for (Pprojectile projectile : player.projectiles){
			for (Enemy enemy : observers){
				if (!enemy.isExploding()){
					double dx = enemy.getPositionX() - projectile.getPositionX();
					double dy = enemy.getPositionY() - projectile.getPositionY();
					double dist = Math.sqrt(dx * dx + dy * dy);
					if (dist < enemy.getRadius()){
						enemy.setExploding();
						explodingEnemys.add(enemy);
					}
				}
			}
		}
	}
	
	public void launchEnemy (){
		/* verificando se novos inimigos (tipo 1) devem ser "lançados" */
		//lançando um ship
		
		if(currentTime > Ship.getNext()){
			observers.add(new Ship());
			Ship.setNext(Level.currentTime + 500);
		}
		
		if(currentTime > Worm.getNext()){
			observers.add(new Worm());
		}
	}
	
	public void run(){
		/* Usada para atualizar o estado dos elementos do jogo    */
		/* (player, projéteis e inimigos) "delta" indica quantos  */
		/* ms se passaram desde a última atualização.             */
		
		delta = System.currentTimeMillis() - currentTime;
		
		/* Já a variável "currentTime" nos dá o timestamp atual.  */
		
		currentTime = System.currentTimeMillis();
		
		
		/***************************/
		/* Verificação de colisões */
		/***************************/
					
		if (!player.isExploding()) verifyPlayerColision();
		verifyEnemyColision();
		
		
		/***************************/
		/* Atualizações de estados */
		/***************************/
		
		LinkedList <Enemy> inactiveEnemys = new LinkedList <Enemy>();
		
		for (Enemy enemy : observers){
			if (enemy.isOutOfScreen() && enemy.projectiles.size() == 0){
				inactiveEnemys.add(enemy);
			} else {
				enemy.atualiza();
			}
		}
		
		launchEnemy();
		player.atualiza();
		
		/*******************/
		/* Desenho da cena */
		/*******************/
		
		player.desenha();
		for (Enemy enemy : observers) enemy.desenha();
		
		//Elimina os inimigos destruidos da lista de observadores
		for (Enemy enemy : explodingEnemys){
			if (currentTime > enemy.getExplosion_end() && enemy.projectiles.size() == 0){
				inactiveEnemys.add(enemy);
			}
		}
		for (Enemy enemy : inactiveEnemys) observers.remove(enemy);
		
	}
	
	
}
