package refatorado;

import java.awt.Color;

class Eprojectile {
	/* vari�veis dos proj�teis lan�ados pelos inimigos (tanto tipo 1, quanto tipo 2) */
	
	int [] e_projectile_states;				// estados
	double [] e_projectile_X;				// coordenadas x
	double [] e_projectile_Y;				// coordenadas y
	double [] e_projectile_VX;				// velocidade no eixo x
	double [] e_projectile_VY;				// velocidade no eixo y
	double e_projectile_radius;				// raio (tamanho dos proj�teis inimigos)
	
	Eprojectile(){

		e_projectile_states = new int[200];
		e_projectile_X = new double[200];
		e_projectile_Y = new double[200];
		e_projectile_VX = new double[200];
		e_projectile_VY = new double[200];
		e_projectile_radius = 2.0;

	}
	
	void inicializaEprojectile(){
		for(int i = 0; i < e_projectile_states.length; i++) e_projectile_states[i] = Main.INACTIVE;
	}
	
	void atualizaEprojectile(){
		/* projeteis (inimigos) */
		
		for(int i = 0; i < e_projectile_states.length; i++){
			
			if(e_projectile_states[i] == Main.ACTIVE){
				
				/* verificando se proj�til saiu da tela */
				if(e_projectile_Y[i] > GameLib.HEIGHT) {
					
					e_projectile_states[i] = Main.INACTIVE;
				}
				//se o proj�til inimigo n�o sair da tela ele se movimenta
				else {
				
					e_projectile_X[i] += e_projectile_VX[i] * Main.delta;
					e_projectile_Y[i] += e_projectile_VY[i] * Main.delta;
				}
			}
		}
	}
	void desenhaEprojectile(){
		/* desenhando projeteis (inimigos) */
		
		for(int i = 0; i < e_projectile_states.length; i++){
			
			if(e_projectile_states[i] == Main.ACTIVE){

				GameLib.setColor(Color.RED);
				GameLib.drawCircle(e_projectile_X[i], e_projectile_Y[i], e_projectile_radius);
			}
		}
	}
}
