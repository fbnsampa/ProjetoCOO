package refatorado;
import java.awt.Color;

import refatorado.Eprojectile;

class Player {
	
	int player_state;								// estado
	double player_X;								// coordenada x
	double player_Y;								// coordenada y
	double player_VX;								// velocidade no eixo x
	double player_VY;								// velocidade no eixo y
	double player_radius;							// raio (tamanho aproximado do player)
	double player_explosion_start;					// instante do início da explosão
	double player_explosion_end;					// instante do final da explosão
	long player_nextShot;							// instante a partir do qual pode haver um próximo tiro


	Player(){
		player_state = Main.ACTIVE;
		player_X = GameLib.WIDTH / 2;
		player_Y = GameLib.HEIGHT * 0.90;
		player_VX = 0.25;
		player_VY = 0.25;
		player_radius = 12.0;
		player_explosion_start = 0;
		player_explosion_end = 0;
		player_nextShot = Main.currentTime;
	}
	
	void verificaColisaoPlayer(Projectile pprojectile, Eprojectile eprojectile, Enemy1 enemy1, Enemy2 enemy2){
		if(player_state == Main.ACTIVE){
			
			/* colisões player - projeteis (inimigo) */
			
			for(int i = 0; i < eprojectile.e_projectile_states.length; i++){
				//dx, dy e dist são as distâncias entre os projéteis inimigos e
				//o personagem
				double dx = eprojectile.e_projectile_X[i] - player_X;
				double dy = eprojectile.e_projectile_Y[i] - player_Y;
				double dist = Math.sqrt(dx * dx + dy * dy);
				//aqui se a distancia for menor que a soma do raio do projétil
				//do inimigo e do jogador, ocorre uma explosão
				if(dist < (player_radius + eprojectile.e_projectile_radius) * 0.8){
					
					player_state = Main.EXPLODING;
					player_explosion_start = Main.currentTime;
					player_explosion_end = Main.currentTime + 2000;
				}
			}
		
			/* colisões player - inimigos */
						
			for(int i = 0; i < enemy1.enemy1_states.length; i++){
				//dx, dy e dist são a distância entre o personagem
				//e o inimigo 1
				double dx = enemy1.enemy1_X[i] - player_X;
				double dy = enemy1.enemy1_Y[i] - player_Y;
				double dist = Math.sqrt(dx * dx + dy * dy);
				//aqui é testado se o inimigo1 entrou em colisão com o jogador
				//caso tenha, ocorre uma explosão
				if(dist < (player_radius + enemy1.enemy1_radius) * 0.8){
					
					player_state = Main.EXPLODING;
					player_explosion_start = Main.currentTime;
					player_explosion_end = Main.currentTime + 2000;
				}
			}
			
			for(int i = 0; i < enemy2.enemy2_states.length; i++){
				//dx, dy e dist são a distância entre o personagem
				//e o inimigo 2
				double dx = enemy2.enemy2_X[i] - player_X;
				double dy = enemy2.enemy2_Y[i] - player_Y;
				double dist = Math.sqrt(dx * dx + dy * dy);
				//aqui é testado se o inimigo2 entrou em colisão com o jogador
				//caso tenha, ocorre uma explosão					
				if(dist < (player_radius + enemy2.enemy2_radius) * 0.8){
					
					player_state = Main.EXPLODING;
					player_explosion_start = Main.currentTime;
					player_explosion_end = Main.currentTime + 2000;
				}
			}
		}
		/* colisões projeteis (player) - inimigos */
		
		for(int k = 0; k < pprojectile.projectile_states.length; k++){
			
			for(int i = 0; i < enemy1.enemy1_states.length; i++){
				//se os inimigos1 na tela estiverem ACTIVE será feito o cálculo
				//da distância entre os projéteis do jogador e o inimigo1 ativo
				if(enemy1.enemy1_states[i] == Main.ACTIVE){
				
					double dx = enemy1.enemy1_X[i] - pprojectile.projectile_X[k];
					double dy = enemy1.enemy1_Y[i] - pprojectile.projectile_Y[k];
					double dist = Math.sqrt(dx * dx + dy * dy);
					//caso a condição seja satisfeita o inimigo1 será explodido
					if(dist < enemy1.enemy1_radius){
						
						enemy1.enemy1_states[i] = Main.EXPLODING;
						enemy1.enemy1_explosion_start[i] = Main.currentTime;
						enemy1.enemy1_explosion_end[i] = Main.currentTime + 500;
					}
				}
			}
			
			for(int i = 0; i < enemy2.enemy2_states.length; i++){
				//se os inimigos2 na tela estiverem ACTIVE será feito o cálculo
				//da distância entre os projéteis do jogador e o inimigo2 ativo					
				if(enemy2.enemy2_states[i] == Main.ACTIVE){
					
					double dx = enemy2.enemy2_X[i] - pprojectile.projectile_X[k];
					double dy = enemy2.enemy2_Y[i] - pprojectile.projectile_Y[k];
					double dist = Math.sqrt(dx * dx + dy * dy);
					//caso a condição seja satisfeita o inimigo1 será explodido
					if(dist < enemy2.enemy2_radius){
						
						enemy2.enemy2_states[i] = Main.EXPLODING;
						enemy2.enemy2_explosion_start[i] = Main.currentTime;
						enemy2.enemy2_explosion_end[i] = Main.currentTime + 500;
					}
				}
			}
		}
	}
	void verificaExplosionPlayer(){
		if(player_state == Main.EXPLODING){
			
			if(Main.currentTime > player_explosion_end){
				
				player_state = Main.ACTIVE;
			}
		}
	}
	
	void verificaEntradaPlayer(Projectile pprojectile){
		/********************************************/
		/* Verificando entrada do usuário (teclado) */
		/********************************************/
		
		if(player_state == Main.ACTIVE){
			
			if(GameLib.iskeyPressed(GameLib.KEY_UP)) player_Y -= Main.delta * player_VY;
			if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) player_Y += Main.delta * player_VY;
			if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) player_X -= Main.delta * player_VX;
			if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) player_X += Main.delta * player_VY;
			if(GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
				
				if(Main.currentTime > player_nextShot){
					
					int free = Main.findFreeIndex(pprojectile.projectile_states);
					//aqui são instanciados os projéteis do personagem						
					if(free < pprojectile.projectile_states.length){
						
						pprojectile.projectile_X[free] = player_X;
						pprojectile.projectile_Y[free] = player_Y - 2 * player_radius;
						pprojectile.projectile_VX[free] = 0.0;
						pprojectile.projectile_VY[free] = -1.0;
						pprojectile.projectile_states[free] = 1;
						player_nextShot = Main.currentTime + 100;
					}
				}	
			}
		}
		
		if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) Main.running = false;
		
		/* Verificando se coordenadas do player ainda estão dentro	*/
		/* da tela de jogo após processar entrada do usuário.       */
		
		if(player_X < 0.0) player_X = 0.0;
		if(player_X >= GameLib.WIDTH) player_X = GameLib.WIDTH - 1;
		if(player_Y < 25.0) player_Y = 25.0;
		if(player_Y >= GameLib.HEIGHT) player_Y = GameLib.HEIGHT - 1;
	}
	
	void desenhaPlayer(){
		/* desenhando player */
		
		if(player_state == Main.EXPLODING){
			
			double alpha = (Main.currentTime - player_explosion_start) / (player_explosion_end - player_explosion_start);
			GameLib.drawExplosion(player_X, player_Y, alpha);
		}
		else{
			
			GameLib.setColor(Color.BLUE);
			GameLib.drawPlayer(player_X, player_Y, player_radius);
		}
	}

}


