package refatorado;

import java.awt.Color;

class Enemy2 {
	/* variáveis dos inimigos tipo 2 */
	
	int [] enemy2_states;				// estados
	double [] enemy2_X;					// coordenadas x
	double [] enemy2_Y;					// coordenadas y
	double [] enemy2_V;					// velocidades
	double [] enemy2_angle;				// ângulos (indicam direção do movimento)
	double [] enemy2_RV;				// velocidades de rotação
	double [] enemy2_explosion_start;	// instantes dos inícios das explosões
	double [] enemy2_explosion_end;		// instantes dos finais das explosões
	double enemy2_spawnX;				// coordenada x do próximo inimigo tipo 2 a aparecer
	int enemy2_count;					// contagem de inimigos tipo 2 (usada na "formação de voo")
	double enemy2_radius;				// raio (tamanho aproximado do inimigo 2)
	long nextEnemy2;					// instante em que um novo inimigo 2 deve aparecer

	Enemy2(){
		enemy2_states = new int[10];
		enemy2_X = new double[10];
		enemy2_Y = new double[10];
		enemy2_V = new double[10];
		enemy2_angle = new double[10];
		enemy2_RV = new double[10];
		enemy2_explosion_start = new double[10];
		enemy2_explosion_end = new double[10];
		enemy2_spawnX = GameLib.WIDTH * 0.20;
		enemy2_count = 0;
		enemy2_radius = 12.0;
		nextEnemy2 = Main.currentTime + 7000;
	}
	
	void inicializaEnemy2(){
		for(int i = 0; i < enemy2_states.length; i++) enemy2_states[i] = Main.INACTIVE;
	}
	
	void atualizaEnemy2(Eprojectile eprojectile){
		/* inimigos tipo 2 */
		
		for(int i = 0; i < enemy2_states.length; i++){
			//se o inimigo for explodido aquela posição do vetor passa a ser inativa
			if(enemy2_states[i] == Main.EXPLODING){
				
				if(Main.currentTime > enemy2_explosion_end[i]){
					
					enemy2_states[i] = Main.INACTIVE;
				}
			}
			
			if(enemy2_states[i] == Main.ACTIVE){
				
				/* verificando se inimigo saiu da tela */
				if(	enemy2_X[i] < -10 || enemy2_X[i] > GameLib.WIDTH + 10 ) {
					
					enemy2_states[i] = Main.INACTIVE;
				}
				else {
					
					boolean shootNow = false;//variável que autoriza o uso de projetéis
					double previousY = enemy2_Y[i];
					//caso o inimigo2 esteja ativo são feitos os calculos e atribuições
					//para determinar sua movimentação
					enemy2_X[i] += enemy2_V[i] * Math.cos(enemy2_angle[i]) * Main.delta;
					enemy2_Y[i] += enemy2_V[i] * Math.sin(enemy2_angle[i]) * Main.delta * (-1.0);
					enemy2_angle[i] += enemy2_RV[i] * Main.delta;
					
					double threshold = GameLib.HEIGHT * 0.30;
					//dependendo de onde o inimigo 2 estiver na tela a sua rotação
					//é modificada
					if(previousY < threshold && enemy2_Y[i] >= threshold) {
						
						if(enemy2_X[i] < GameLib.WIDTH / 2) enemy2_RV[i] = 0.003;
						else enemy2_RV[i] = -0.003;
					}
					//aqui dependendo de algumas condições é ativada a variavél
					//shootNow que permite o disparo de projéteis pelo inimigo2
					if(enemy2_RV[i] > 0 && Math.abs(enemy2_angle[i] - 3 * Math.PI) < 0.05){
						
						enemy2_RV[i] = 0.0;
						enemy2_angle[i] = 3 * Math.PI;
						shootNow = true;
					}
					
					if(enemy2_RV[i] < 0 && Math.abs(enemy2_angle[i]) < 0.05){
						
						enemy2_RV[i] = 0.0;
						enemy2_angle[i] = 0.0;
						shootNow = true;
					}
					//aqui é determinado como os disparos de inimigo2 serão feitos						
					if(shootNow){

						double [] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };
						// aqui são procuradas instâncias livres de projéteis para serem usadas
						int [] freeArray = Main.findFreeIndex(eprojectile.e_projectile_states, angles.length);

						for(int k = 0; k < freeArray.length; k++){
							
							int free = freeArray[k];
							
							if(free < eprojectile.e_projectile_states.length){
								
								double a = angles[k] + Math.random() * Math.PI/6 - Math.PI/12;
								double vx = Math.cos(a);
								double vy = Math.sin(a);
									
								eprojectile.e_projectile_X[free] = enemy2_X[i];
								eprojectile.e_projectile_Y[free] = enemy2_Y[i];
								eprojectile.e_projectile_VX[free] = vx * 0.30;
								eprojectile.e_projectile_VY[free] = vy * 0.30;
								eprojectile.e_projectile_states[free] = 1;
							}
						}
					}
				}
			}
		}
	}
	
	void verificaEnemy2(){
		/* verificando se novos inimigos (tipo 2) devem ser "lançados" */
		
		if(Main.currentTime > nextEnemy2){
			
			int free = Main.findFreeIndex(enemy2_states);
			//instancia inimigos2				
			if(free < enemy2_states.length){
				
				enemy2_X[free] = enemy2_spawnX;
				enemy2_Y[free] = -10.0;
				enemy2_V[free] = 0.42;
				enemy2_angle[free] = (3 * Math.PI) / 2;
				enemy2_RV[free] = 0.0;
				enemy2_states[free] = Main.ACTIVE;

				enemy2_count++;
				
				if(enemy2_count < 10){
					
					nextEnemy2 = Main.currentTime + 120;
				}
				else {
					
					enemy2_count = 0;
					enemy2_spawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
					nextEnemy2 = (long) (Main.currentTime + 3000 + Math.random() * 3000);
				}
			}
		}
	}
	
	void desenhaEnemy2(){
		/* desenhando inimigos (tipo 2) */
		
		for(int i = 0; i < enemy2_states.length; i++){
			
			if(enemy2_states[i] == Main.EXPLODING){
				
				double alpha = (Main.currentTime - enemy2_explosion_start[i]) / (enemy2_explosion_end[i] - enemy2_explosion_start[i]);
				GameLib.drawExplosion(enemy2_X[i], enemy2_Y[i], alpha);
			}
			
			if(enemy2_states[i] == Main.ACTIVE){
		
				GameLib.setColor(Color.MAGENTA);
				GameLib.drawDiamond(enemy2_X[i], enemy2_Y[i], enemy2_radius);
			}
		}
	}
}
