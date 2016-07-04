package refatorado;
import java.awt.Color;

class Eprojectile extends Projectile {
	/* variáveis dos projéteis lançados pelos inimigos (tanto tipo 1, quanto tipo 2) */
	
	double radius;				// raio (tamanho dos projéteis inimigos)
	
	Eprojectile(){
		super(200);
		int size = 200; 
		states = new int[size];
		position = new Cordinate [size];
		speedy = new Cordinate [size];
		for (int i = 0; i < size; i++){
			position[i] = new Cordinate();
			speedy[i] = new Cordinate();
		}
		radius = 2.0;
	}
	
	void desenha(){
		/* desenhando projeteis (inimigos) */
		
		for(int i = 0; i < states.length; i++){
			
			if(states[i] == Main.ACTIVE){

				GameLib.setColor(Color.RED);
				GameLib.drawCircle(position[i].x, position[i].y, radius);
			}
		}
	}
}
