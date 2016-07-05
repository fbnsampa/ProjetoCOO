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
		//Carrega as configura��es no documento de texto
		
	}
	
	//Verifica se alguma colisao explodiu o player
	//Se for verdade, altera o estado de player e interrompe a execu��o
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

	//Verifica se os proj�teis atirados pelo player destruiu algum inimigo
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
		/* (player, proj�teis e inimigos) "delta" indica quantos  */
		/* ms se passaram desde a �ltima atualiza��o.             */
		
		delta = System.currentTimeMillis() - currentTime;
		
		/* J� a vari�vel "currentTime" nos d� o timestamp atual.  */
		
		currentTime = System.currentTimeMillis();
		
		/***************************/
		/* Verifica��o de colis�es */
		/***************************/
					
		if (!player.exploding) verifyPlayerColision();
		LinkedList <Enemy> destroyedEnemys = verifyEnemyColision();
		
		
		/***************************/
		/* Atualiza��es de estados */
		/***************************/
		
		for (Enemy enemy : observers){
			enemy.atualiza();
			enemy.verifica();
		}
		
		/* Verificando se a explos�o do player j� acabou.         */
		/* Ao final da explos�o, o player volta a ser control�vel */
		player.verificaExplosionPlayer();
		
		/********************************************/
		/* Verificando entrada do usu�rio (teclado) */
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
