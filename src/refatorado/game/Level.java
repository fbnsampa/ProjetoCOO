package refatorado.game;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import refatorado.game.enemy.Enemy;
import refatorado.game.enemy.Overlord;
import refatorado.game.enemy.Ship;
import refatorado.game.enemy.Worm;
import refatorado.game.enemy.DeathStar;
import refatorado.game.projectile.Eprojectile;
import refatorado.game.projectile.Pprojectile;

public class Level extends Subject<Enemy>{

	private static long currentTime;
	private static long startTime;
	private static long delta;
	private Player player;
	private LinkedList <Enemy> explodingEnemys;
	private List <Enemy> nextEnemys;
	int count =  0;
	
	public Level (){
		super();
		explodingEnemys = new LinkedList<Enemy>();
		nextEnemys = new LinkedList <Enemy>();
		player = Main.player;
		currentTime = System.currentTimeMillis();
		startTime = currentTime;
		delta = 0;
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
				if (line.length == 5){ //eh um inimigo comum
					int type = Integer.parseInt(line[1]);
					double x = Double.parseDouble(line[3]);
					double y = Double.parseDouble(line[4]);
					long spawn = Long.parseLong(line[2]);
					if (type == 1) nextEnemys.add(new Ship(x, y, spawn));
					else 
						for(int i = 1; i <= 10; i++ )
							nextEnemys.add(new Worm(x, y, spawn + i * 120 ));
				} else { //eh um boss
					int type = Integer.parseInt(line[1]);
					int maxHP = Integer.parseInt(line[2]);
					double x = Double.parseDouble(line[4]);
					double y = Double.parseDouble(line[5]);
					long spawn = Long.parseLong(line[3]);
					if (type == 1) nextEnemys.add(new DeathStar(x, y, spawn,maxHP));
					else nextEnemys.add(new Overlord(x, y, spawn, maxHP));
				}
			}
			
			//Ordenar elementos de nextEnemys em ordem crescente de spawn
			Collections.sort(nextEnemys);

		} catch (FileNotFoundException x){
			System.out.println("'" + name + "'" + " file not found!");
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
				if (player.life.takeHit()) player.setExploding();
				return;
			}
			
			for (Eprojectile projectile : enemy.projectiles){
				dx = projectile.getPositionX() - player.getPositionX();
				dy = projectile.getPositionY() - player.getPositionY();
				dist = Math.sqrt(dx * dx + dy * dy);
				
				if (dist < (player.getRadius() + projectile.getRadius()) * 0.8){
					
					if (player.life.takeHit()) player.setExploding();
					return;
				}
			}
		}
	}

	//Verifica se os projéteis atirados pelo player destruiu algum inimigo
	public void verifyEnemyColision (){
		List <Pprojectile> inactiveProjectiles = new LinkedList<Pprojectile>();
		for (Pprojectile projectile : player.projectiles){
			for (Enemy enemy : observers){
				if (!enemy.isExploding() || enemy.isVulnerable()){
					double dx = enemy.getPositionX() - projectile.getPositionX();
					double dy = enemy.getPositionY() - projectile.getPositionY();
					double dist = Math.sqrt(dx * dx + dy * dy);
					if (dist < enemy.getRadius()){
						if (enemy.life.takeHit()){
							enemy.setExploding();
							explodingEnemys.add(enemy);
						}
						inactiveProjectiles.add(projectile);
					}
				} 
			}
		}
		for (Pprojectile projectile : inactiveProjectiles)
			player.projectiles.remove(projectile);
	}
	
	public void launchEnemyOld (){
		if(currentTime > Ship.getNext()){
			addObserver(new Ship());
			Ship.setNext(Level.currentTime + 500);
		}
		
		if(currentTime > Worm.getNext()){
			addObserver(new Worm());
		}
	}
	
	public void launchEnemy (){
		while (!nextEnemys.isEmpty()){
			if (currentTime - startTime > nextEnemys.get(0).getSpawn()){
				addObserver(nextEnemys.get(0));
				nextEnemys.remove(0);
			} else return;
		}
	}
	
	public void run(){
		count++;
		delta = System.currentTimeMillis() - currentTime;
		
		//A variável "currentTime" nos dá o timestamp atual.
		currentTime = System.currentTimeMillis();
		
		//Verificação de colisões
		if (!player.isExploding() || player.isVulnerable()) verifyPlayerColision();
		verifyEnemyColision();
		
		//Atualizações de estados
		LinkedList <Enemy> inactiveEnemys = new LinkedList <Enemy>();
		
		for (Enemy enemy : observers){
			if (enemy.isOutOfScreen() && enemy.projectiles.size() == 0){
				inactiveEnemys.add(enemy);
			} else {
				enemy.setUpdateON();
			}
		}
		
		launchEnemy();
//		if (observers.size() < 1) addObserver(new DeathStar());
	
		player.update();
		notifyObservers();
		
		// Desenho da cena
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