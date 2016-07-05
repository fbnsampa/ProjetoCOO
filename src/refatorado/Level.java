package refatorado;
import java.util.*;

public class Level extends Subject<Enemy>{

	long currentTime;
	long delta;
	Player player;
	
	
	Pprojectile pprojectile = new Pprojectile();
	Ship ship = new Ship();
	Worm worm = new Worm();
	Eprojectile eprojectile = new Eprojectile();
	
	
	
	public Level (Player player){
		super();
		this.player = player;
		currentTime = 0;
		delta = 0;
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
	public LinkedList<Enemy> verifyEnemyColision (){
		LinkedList <Enemy> resp = new LinkedList <Enemy>();
		for (Pprojectile projectile : player.projectiles){
			for (Enemy enemy : observers){
				if (!enemy.exploding){
					double dx = enemy.position.x -  projectile.position.x;
					double dy = enemy.position.y -  projectile.position.y;
					double dist = Math.sqrt(dx * dx + dy * dy);
					if (dist < enemy.radius){
						enemy.exploding = true;
						enemy.explosion_start = currentTime;
						enemy.explosion_end = currentTime + 500;
						resp.add(enemy);
					}
				}
			}
		}
		return resp;
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
					
		if (!player.exploding) verifyPlayerColision();
		LinkedList <Enemy> destroyedEnemys = verifyEnemyColision();
		
		
		/***************************/
		/* Atualizações de estados */
		/***************************/
		
		for (Enemy enemy : observers){
			enemy.atualiza();
			enemy.verifica();
		}
		
		/* Verificando se a explosão do player já acabou.         */
		/* Ao final da explosão, o player volta a ser controlável */
		player.verificaExplosionPlayer();
		
		/********************************************/
		/* Verificando entrada do usuário (teclado) */
		/********************************************/
		player.verificaEntradaPlayer(pprojectile);
		
		/*******************/
		/* Desenho da cena */
		/*******************/
		
		/* desenhando player */
		player.desenha();
			
		
		
		for (Enemy enemy : observers) enemy.desenha();
		
		//Elimina os inimigos destruidos da lista de observadores
		if (!destroyedEnemys.isEmpty())
			for (Enemy enemy : destroyedEnemys)
				observers.remove(enemy);
		
		
		
	}
	
	
}
