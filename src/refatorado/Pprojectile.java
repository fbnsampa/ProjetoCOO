package refatorado;
import java.awt.Color;

class Pprojectile extends Projectile{
	
	/* variáveis dos projéteis disparados pelo player */

	Pprojectile(double x, double y, double vx, double vy){
		super(x, y, vx, vy);
	}
	
	void desenha(){
		/* desenhando projeteis (player) */
		double X = position.x;
		double Y = position.y;
		GameLib.setColor(Color.GREEN);
		GameLib.drawLine(X, Y - 5, X, Y + 5);
		GameLib.drawLine(X - 1, Y - 3, X - 1, Y + 3);
		GameLib.drawLine(X + 1, Y - 3, X + 1, Y + 3);
	}
}
