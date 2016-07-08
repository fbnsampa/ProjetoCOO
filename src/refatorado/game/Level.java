package refatorado.game;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import refatorado.game.enemy.Enemy;
import refatorado.game.enemy.Ship;
import refatorado.game.enemy.Worm;
import refatorado.game.enemy.Boss;
import refatorado.game.enemy.DeathStar;
import refatorado.game.projectile.Eprojectile;
import refatorado.game.projectile.Pprojectile;

public class Level extends Subject<Enemy>{

	private static long currentTime;
	private static long delta;
	private Player player;
	private LinkedList <Enemy> explodingEnemys;
	private List <Enemy> nextEnemys;
	
	public Level (){
		super();
		explodingEnemys = new LinkedList<Enemy>();
		nextEnemys = new LinkedList <Enemy>();
		player = Main.player;
		currentTime = System.currentTimeMillis();
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
		
		File file = new File (name);
		
		try (Scanner in = new Scanner(file)){
			String [] line;
			
			while (in.hasNext()){
				line = in.nextLine().split(" ");
				Enemy novo;
				if (line.length == 5){ //eh um inimigo comum
					int type = Integer.parseInt(line[1]);
					double x = Double.parseDouble(line[3]);
					double y = Double.parseDouble(line[4]);
					long spawn = Long.parseLong(line[2]);
					if (type == 1) novo = new Ship(x, y, spawn);
					else novo = new Worm(x, y, spawn);
					nextEnemys.add(novo);
				} else { //eh um boss
					int type = Integer.parseInt(line[1]);
					int maxHP = Integer.parseInt(line[2]);
					double x = Double.parseDouble(line[4]);
					double y = Double.parseDouble(line[5]);
					long spawn = Long.parseLong(line[3]);
					if (type == 1) novo = new DeathStar(x, y, spawn,maxHP);
					else novo = new Boss(x, y, spawn, maxHP);
				}
				//Organizar nextEnemys
				Collections.sort(nextEnemys);
			}
			
			
		} catch (FileNotFoundException x){
			System.out.println("File not found!");
			x.printStackTrace();			
		}
		
		
		
		
		
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
	
	public void launchEnemyOld (){
		/* verificando se novos inimigos (tipo 1) devem ser "lançados" */
		//lançando um ship
		
		if(currentTime > Ship.getNext()){
			addObserver(new Ship());
			Ship.setNext(Level.currentTime + 500);
		}
		
		if(currentTime > Worm.getNext()){
			addObserver(new Worm());
		}
	}
	
	public void launchEnemy (){
		/* verificando se novos inimigos (tipo 1) devem ser "lançados" */
		while (!nextEnemys.isEmpty()){
			if (currentTime > nextEnemys.get(0).getSpawn()){
				addObserver(nextEnemys.get(0));
				nextEnemys.remove(0);
			} else return;
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
				enemy.setUpdateON();
			}
		}
		
		//launchEnemy();
		//if (observers.size() < 1) addObserver(new Boss());
		launchEnemyOld();
		player.update();
		notifyObservers();
		
		/*******************/
		/* Desenho da cena */
		/*******************/
		player.draw();
		for (Enemy enemy : observers) enemy.draw();
		
		//Elimina os inimigos destruidos da lista de observadores
		for (Enemy enemy : explodingEnemys){
			if (currentTime > enemy.getExplosion_end() && enemy.projectiles.size() == 0){
				inactiveEnemys.add(enemy);
			}
		}
		
		for (Enemy enemy : inactiveEnemys) removeObserver(enemy);
		
	}
	
	
}
