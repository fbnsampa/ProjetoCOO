package refatorado;

import java.awt.Color;

class Projectile {
	
	/* variáveis dos projéteis disparados pelo player */
	
	int [] projectile_states;				// estados
	double [] projectile_X;					// coordenadas x
	double [] projectile_Y;					// coordenadas y
	double [] projectile_VX;				// velocidades no eixo x
	double [] projectile_VY;				// velocidades no eixo y

	Projectile(){
		projectile_states = new int[10];
		projectile_X = new double[10];
		projectile_Y = new double[10];
		projectile_VX = new double[10];
		projectile_VY = new double[10];
	}
	
	void inicializaProjectile(){
		for(int i = 0; i < projectile_states.length; i++) projectile_states[i] = Main.INACTIVE;
	}
	
	void atualizaProjectile(){
		/* projeteis (player) */
		
		for(int i = 0; i < projectile_states.length; i++){
			
			if(projectile_states[i] == Main.ACTIVE){
				
				/* verificando se projétil saiu da tela */
				if(projectile_Y[i] < 0) {
					
					projectile_states[i] = Main.INACTIVE;
				}
				else {
				
					projectile_X[i] += projectile_VX[i] * Main.delta;
					projectile_Y[i] += projectile_VY[i] * Main.delta;
				}
			}
		}
	}
	
	void desenhaProjectile(){
		/* desenhando projeteis (player) */
		
		for(int i = 0; i < projectile_states.length; i++){
			
			if(projectile_states[i] == Main.ACTIVE){
				
				GameLib.setColor(Color.GREEN);
				GameLib.drawLine(projectile_X[i], projectile_Y[i] - 5, projectile_X[i], projectile_Y[i] + 5);
				GameLib.drawLine(projectile_X[i] - 1, projectile_Y[i] - 3, projectile_X[i] - 1, projectile_Y[i] + 3);
				GameLib.drawLine(projectile_X[i] + 1, projectile_Y[i] - 3, projectile_X[i] + 1, projectile_Y[i] + 3);
			}
		}
	}
}
