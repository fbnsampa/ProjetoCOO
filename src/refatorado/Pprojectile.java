package refatorado;
import java.awt.Color;

class Pprojectile extends Projectile{
	
	/* variáveis dos projéteis disparados pelo player */

	Pprojectile(){
		super();
		//int size = 10;
		//states = new int[size];
		//position = new Cordinate [size];
		//speedy = new Cordinate [size];
		/*for (int i = 0; i < size; i++){
			position[i] = new Cordinate();
			speedy[i] = new Cordinate();
		}*/
	}
	
	void desenha(){
		/* desenhando projeteis (player) */
		//for(int i = 0; i < states.length; i++){
			if(states[i] == Main.ACTIVE){
				double X = position.x;
				double Y = position.y;
				GameLib.setColor(Color.GREEN);
				GameLib.drawLine(X, Y - 5, X, Y + 5);
				GameLib.drawLine(X - 1, Y - 3, X - 1, Y + 3);
				GameLib.drawLine(X + 1, Y - 3, X + 1, Y + 3);
			}
		//}
	}
}
