package refatorado;
import java.awt.Color;

class Player implements Observer{
	
	//int state;								// estado
	Cordinate position;
	Cordinate speedy;
	double radius;							// raio (tamanho aproximado do player)
	double explosion_start;					// instante do início da explosão
	double explosion_end;					// instante do final da explosão
	long nextShot;							// instante a partir do qual pode haver um próximo tiro
	boolean exploding;

	Player(){
		//state = Main.ACTIVE;
		position = new Cordinate(GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90);
		speedy = new Cordinate (0.25, 0.25);
		radius = 12.0;
		explosion_start = 0;
		explosion_end = 0;
		nextShot = Main.currentTime;
		exploding = false;
	}
	
	void verificaColisaoPlayer(Pprojectile pprojectile, Eprojectile eprojectile, Ship ship, Worm worm){
		if(state == Main.ACTIVE){
			
			/* colisões player - projeteis (inimigo) */
			
			for(int i = 0; i < eprojectile.states.length; i++){
				//dx, dy e dist são as distâncias entre os projéteis inimigos e
				//o personagem
				double dx = eprojectile.position[i].x - position.x;
				double dy = eprojectile.position[i].y - position.y;
				double dist = Math.sqrt(dx * dx + dy * dy);
				//aqui se a distancia for menor que a soma do raio do projétil
				//do inimigo e do jogador, ocorre uma explosão
				if(dist < (radius + eprojectile.radius) * 0.8){
					
					state = Main.EXPLODING;//a ser removido
					exploding = true;
					explosion_start = Main.currentTime;
					explosion_end = Main.currentTime + 2000;
				}
			}
		
			/* colisões player - inimigos */
						
			for(int i = 0; i < ship.states.length; i++){
				//dx, dy e dist são a distância entre o personagem
				//e o inimigo 1
				double dx = ship.position[i].x - position.x;
				double dy = ship.position[i].y - position.y;
				double dist = Math.sqrt(dx * dx + dy * dy);
				//aqui é testado se o inimigo1 entrou em colisão com o jogador
				//caso tenha, ocorre uma explosão
				if(dist < (radius + ship.radius) * 0.8){
					state = Main.EXPLODING;//a ser removido
					exploding = true;
					explosion_start = Main.currentTime;
					explosion_end = Main.currentTime + 2000;
				}
			}
			
			for(int i = 0; i < worm.states.length; i++){
				//dx, dy e dist são a distância entre o personagem
				//e o inimigo 2
				double dx = worm.position[i].x - position.x;
				double dy = worm.position[i].y - position.y;
				double dist = Math.sqrt(dx * dx + dy * dy);
				//aqui é testado se o inimigo2 entrou em colisão com o jogador
				//caso tenha, ocorre uma explosão					
				if(dist < (radius + worm.radius) * 0.8){
					state = Main.EXPLODING;// a ser removido
					exploding = true;
					explosion_start = Main.currentTime;
					explosion_end = Main.currentTime + 2000;
				}
			}
		}
		/* colisões projeteis (player) - inimigos */
		
		for(int k = 0; k < pprojectile.states.length; k++){
			
			for(int i = 0; i < ship.states.length; i++){
				//se os inimigos1 na tela estiverem ACTIVE será feito o cálculo
				//da distância entre os projéteis do jogador e o inimigo1 ativo
				if(ship.states[i] == Main.ACTIVE){
				
					double dx = ship.position[i].x - pprojectile.position[k].x;
					double dy = ship.position[i].y - pprojectile.position[k].y;
					double dist = Math.sqrt(dx * dx + dy * dy);
					
					//caso a condição seja satisfeita o inimigo1 será explodido
					if(dist < ship.radius){
						
						ship.states[i] = Main.EXPLODING;// a ser excluido
						ship.exploding = true;
						ship.explosion_start[i] = Main.currentTime;
						ship.explosion_end[i] = Main.currentTime + 500;
					}
				}
			}
			
			for(int i = 0; i < worm.states.length; i++){
				//se os inimigos2 na tela estiverem ACTIVE será feito o cálculo
				//da distância entre os projéteis do jogador e o inimigo2 ativo					
				if(worm.states[i] == Main.ACTIVE){
					
					double dx = worm.position[i].x - pprojectile.position[k].x;
					double dy = worm.position[i].y - pprojectile.position[k].y;
					double dist = Math.sqrt(dx * dx + dy * dy);
					
					//caso a condição seja satisfeita o inimigo1 será explodido
					if(dist < worm.radius){
						worm.states[i] = Main.EXPLODING;// a ser excluido
						worm.exploding = true;
						worm.explosion_start[i] = Main.currentTime;
						worm.explosion_end[i] = Main.currentTime + 500;
					}
				}
			}
		}
	}
	void verificaExplosionPlayer(){
		if(state == Main.EXPLODING){
			if(Main.currentTime > explosion_end){
				state = Main.ACTIVE;
			}
		}
	}
	
	void verificaEntradaPlayer(Pprojectile pprojectile){
		/********************************************/
		/* Verificando entrada do usuário (teclado) */
		/********************************************/
		
		if(state == Main.ACTIVE){
			
			if(GameLib.iskeyPressed(GameLib.KEY_UP)) position.y -= Main.delta * speedy.y;
			if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) position.y += Main.delta * speedy.y;
			if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) position.x -= Main.delta * speedy.x;
			if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) position.x += Main.delta * speedy.x;
			if(GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
				
				if(Main.currentTime > nextShot){
					
					int free = Main.findFreeIndex(pprojectile.states);
					//aqui são instanciados os projéteis do personagem						
					if(free < pprojectile.states.length){
						
						pprojectile.position[free].x = position.x;
						pprojectile.position[free].y = position.y - 2 * radius;
						pprojectile.speedy[free].x = 0.0;
						pprojectile.speedy[free].y = -1.0;
						pprojectile.states[free] = 1;
						nextShot = Main.currentTime + 100;
					}
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
		/* desenhando player */
		
		if(state == Main.EXPLODING){
			
			double alpha = (Main.currentTime - explosion_start) / (explosion_end - explosion_start);
			GameLib.drawExplosion(position.x, position.y, alpha);
		}
		else{
			
			GameLib.setColor(Color.BLUE);
			GameLib.drawPlayer(position.x, position.y, radius);
		}
	}
	
	public void atualiza(){
		
	}

}


