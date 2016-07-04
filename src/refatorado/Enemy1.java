package refatorado;

import java.awt.Color;

class Enemy1 {
	/* variáveis dos inimigos tipo 1 */
	
	int [] enemy1_states;				// estados
	double [] enemy1_X;					// coordenadas x
	double [] enemy1_Y;					// coordenadas y
	double [] enemy1_V;					// velocidades
	double [] enemy1_angle;				// ângulos (indicam direção do movimento)
	double [] enemy1_RV;				// velocidades de rotação
	double [] enemy1_explosion_start;	// instantes dos inícios das explosões
	double [] enemy1_explosion_end;		// instantes dos finais da explosões
	long [] enemy1_nextShoot;			// instantes do próximo tiro
	double enemy1_radius;				// raio (tamanho do inimigo 1)
	long nextEnemy1;					// instante em que um novo inimigo 1 deve aparecer

	Enemy1(){
		
		enemy1_states = new int[10];
		enemy1_X = new double[10];
		enemy1_Y = new double[10];
		enemy1_V = new double[10];
		enemy1_angle = new double[10];
		enemy1_RV = new double[10];
		enemy1_explosion_start = new double[10];
		enemy1_explosion_end = new double[10];
		enemy1_nextShoot = new long[10];
		enemy1_radius = 9.0;
		nextEnemy1 = Main.currentTime + 2000;
	}
	
	void inicializaEnemy1(){
		for(int i = 0; i < enemy1_states.length; i++) enemy1_states[i] = Main.INACTIVE;
	}
	
	void atualizaEnemy1(Eprojectile eprojectile, Player player){
		/* inimigos tipo 1 */
		
		for(int i = 0; i < enemy1_states.length; i++){
			//se o inimigo for explodido aquela posição do vetor passa a ser inativa
			if(enemy1_states[i] == Main.EXPLODING){
				
				if(Main.currentTime > enemy1_explosion_end[i]){
					
					enemy1_states[i] = Main.INACTIVE;
				}
			}
			
			if(enemy1_states[i] == Main.ACTIVE){
				
				/* verificando se inimigo saiu da tela */
				if(enemy1_Y[i] > GameLib.HEIGHT + 10) {
					//se ele sair da tela a instância fica como inativa
					//para poder liberar espaço para os novos inimigos1
					enemy1_states[i] = Main.INACTIVE;
				}
				else {
					//se ele não sair da tela, sua posição e ângulo é alterada por meio de um
					//cálculo de velocidade
					enemy1_X[i] += enemy1_V[i] * Math.cos(enemy1_angle[i]) * Main.delta;
					enemy1_Y[i] += enemy1_V[i] * Math.sin(enemy1_angle[i]) * Main.delta * (-1.0);
					enemy1_angle[i] += enemy1_RV[i] * Main.delta;
					//se o inimigo1 estiver acima do personagem e o tempo atual for maior
					//que o tempo do próximo tiro acontece um disparo
					if(Main.currentTime > enemy1_nextShoot[i] && enemy1_Y[i] < player.player_Y){
						//caso a condição seja satisfeita será procurado um índice de instância 
						//INACTIVE de projétil
						int free = Main.findFreeIndex(eprojectile.e_projectile_states);
						//Aqui o projétil será instanciado com o índice encontrado
						if(free < eprojectile.e_projectile_states.length){
			
							eprojectile.e_projectile_X[free] = enemy1_X[i];
							eprojectile.e_projectile_Y[free] = enemy1_Y[i];
							eprojectile.e_projectile_VX[free] = Math.cos(enemy1_angle[i]) * 0.45;
							eprojectile.e_projectile_VY[free] = Math.sin(enemy1_angle[i]) * 0.45 * (-1.0);
							eprojectile.e_projectile_states[free] = 1;
							//o tempo do próximo disparo é atualizado
							enemy1_nextShoot[i] = (long) (Main.currentTime + 200 + Math.random() * 500);
						}
					}
				}
			}
		}
	}
	
	void verificaEnemy1(){
		/* verificando se novos inimigos (tipo 1) devem ser "lançados" */
		
		if(Main.currentTime > nextEnemy1){
			
			int free = Main.findFreeIndex(enemy1_states);
			//instancia novos inimigos 1				
			if(free < enemy1_states.length){
				
				enemy1_X[free] = Math.random() * (GameLib.WIDTH - 20.0) + 10.0;
				enemy1_Y[free] = -10.0;
				enemy1_V[free] = 0.20 + Math.random() * 0.15;
				enemy1_angle[free] = 3 * Math.PI / 2;
				enemy1_RV[free] = 0.0;
				enemy1_states[free] = Main.ACTIVE;
				enemy1_nextShoot[free] = Main.currentTime + 500;
				nextEnemy1 = Main.currentTime + 500;
			}
		}
	}
	
	void desenhaEnemy1(){
		/* desenhando inimigos (tipo 1) */
		
		for(int i = 0; i < enemy1_states.length; i++){
			
			if(enemy1_states[i] == Main.EXPLODING){
				
				double alpha = (Main.currentTime - enemy1_explosion_start[i]) / (enemy1_explosion_end[i] - enemy1_explosion_start[i]);
				GameLib.drawExplosion(enemy1_X[i], enemy1_Y[i], alpha);
			}
			
			if(enemy1_states[i] == Main.ACTIVE){
		
				GameLib.setColor(Color.CYAN);
				GameLib.drawCircle(enemy1_X[i], enemy1_Y[i], enemy1_radius);
			}
		}
	}
	
}
