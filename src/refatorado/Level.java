package refatorado;
import java.util.*;

public class Level extends Subject<Enemy>{

	static long currentTime;
	static long delta;
	Player player;
	LinkedList <Enemy> explodingEnemys;

	
	public Level (){
		super();
		explodingEnemys = new LinkedList<Enemy>();
		player = Main.player;
		currentTime = 0;
		delta = 0;
		Ship.next = System.currentTimeMillis() + 2000; //bizarro porem provisorio
		Worm.next = System.currentTimeMillis() + 7000; //bizarro porem provisorio
	}
	
	public void load (String name){
		//Carrega as configurações no documento de texto
		
	}
	
	//Verifica se alguma colisao explodiu o player
	//Se for verdade, altera o estado de player e interrompe a execução
	public void verifyPlayerColision (){
		for (Enemy enemy : observers){
			double dx, dy, dist;
			dx = enemy.position.x - player.position.x;
			dy = enemy.position.y - player.position.y;
			dist = Math.sqrt(dx * dx + dy * dy);

			if (dist < (player.radius + enemy.radius) * 0.8){
				player.exploding = true;
				player.explosion_start = currentTime;
				player.explosion_end = currentTime + 2000;
				return;
			}
			
			for (Eprojectile projectile : enemy.projectiles){
				dx = projectile.position.x - player.position.x;
				dy = projectile.position.y - player.position.y;
				dist = Math.sqrt(dx * dx + dy * dy);
				
				if (dist < (player.radius + projectile.radius) * 0.8){
					player.exploding = true;
					player.explosion_start = currentTime;
					player.explosion_end = currentTime + 2000;
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
				if (!enemy.exploding){
					double dx = enemy.position.x - projectile.position.x;
					double dy = enemy.position.y - projectile.position.y;
					double dist = Math.sqrt(dx * dx + dy * dy);
					if (dist < enemy.radius){
						enemy.exploding = true;
						enemy.explosion_start = currentTime;
						enemy.explosion_end = currentTime + 500;
						explodingEnemys.add(enemy);
					}
				}
			}
		}
	}
	
	public void launchEnemy (){
		/* verificando se novos inimigos (tipo 1) devem ser "lançados" */
		//lançando um ship
		
		if(currentTime > Ship.next){
			observers.add(new Ship());
			Ship.next = Level.currentTime + 500;
		}
		
		if(currentTime > Worm.next){
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
		
		//System.out.println(currentTime + " : " + observers.size());
		
		
		/***************************/
		/* Verificação de colisões */
		/***************************/
					
		if (!player.exploding) verifyPlayerColision();
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
		
		//if (!observers.isEmpty()) System.out.println(observers.get(0).projectiles.size());
		
		/*******************/
		/* Desenho da cena */
		/*******************/
		
		player.desenha();
		for (Enemy enemy : observers) enemy.desenha();
		
		//Elimina os inimigos destruidos da lista de observadores
		for (Enemy enemy : explodingEnemys){
			if (!enemy.exploding){
				inactiveEnemys.add(enemy);
			}
		}
		for (Enemy enemy : inactiveEnemys) observers.remove(enemy);
		
	}
	
	
}
