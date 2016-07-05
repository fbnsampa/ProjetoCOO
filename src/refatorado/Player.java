package refatorado;
import java.awt.Color;
import java.util.*;

class Player implements Observer{
	List <Pprojectile> projectiles;
	Cordinate position;
	Cordinate speedy;
	double radius;						// raio (tamanho aproximado do player)
	long explosion_start;				// instante do início da explosão
	long explosion_end;					// instante do final da explosão
	long nextShot;						// instante a partir do qual pode haver um próximo tiro
	boolean exploding;

	Player(){
		//state = Main.ACTIVE;
		projectiles = new ArrayList <Pprojectile>();
		position = new Cordinate(GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90);
		speedy = new Cordinate (0.25, 0.25);
		radius = 12.0;
		explosion_start = 0;
		explosion_end = 0;
		nextShot = Level.currentTime;
		exploding = false;
	}
	
	void verificaExplosionPlayer(){
		if(exploding){
			if(Level.currentTime > explosion_end){
				//state = Main.ACTIVE;
				exploding = false;
			}
		}
	}
	
	void verificaEntradaPlayer(){
		/********************************************/
		/* Verificando entrada do usuário (teclado) */
		/********************************************/
		
		if(!exploding){
			if(GameLib.iskeyPressed(GameLib.KEY_UP)) position.y -= Level.delta * speedy.y;
			if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) position.y += Level.delta * speedy.y;
			if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) position.x -= Level.delta * speedy.x;
			if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) position.x += Level.delta * speedy.x;
			if(GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
				if(Level.currentTime > nextShot){
						projectiles.add(new Pprojectile(position.x, position.y - 2 * radius, 0.0, -1.0));
						nextShot = Level.currentTime + 100;
				}	
			}
		}
		
		if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) Main.running = false;
		
		/* Verificando se coordenadas do player ainda estão dentro	*/
		/* da tela de jogo após processar entrada do usuário.       */
		
		if(position.x < 0.0) position.x = 0.0;
		if(position.x >= GameLib.WIDTH) position.x = GameLib.WIDTH - 1;
		if(position.y < 25.0) position.y = 25.0;
		if(position.y >= GameLib.HEIGHT) position.y = GameLib.HEIGHT - 1;
	}
	
	void desenha(){
		for (Pprojectile projectile : projectiles)
			projectile.desenha();
		
		/* desenhando player */
		if(exploding){
			
			double alpha = (Level.currentTime - explosion_start) / (explosion_end - explosion_start);
			GameLib.drawExplosion(position.x, position.y, alpha);
		}
		else{
			
			GameLib.setColor(Color.BLUE);
			GameLib.drawPlayer(position.x, position.y, radius);
		}
	}
	
	public void atualiza(){
		for (Pprojectile projectile : projectiles){
			if (projectile.position.y < 0){
				projectiles.remove(projectile);
			} else {
				projectile.atualiza();
			}
		}
		verificaExplosionPlayer();
		verificaEntradaPlayer();
	}

}


