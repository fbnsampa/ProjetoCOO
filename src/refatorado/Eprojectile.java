package refatorado;
import java.awt.Color;

class Eprojectile extends Projectile {
	/* variáveis dos projéteis lançados pelos inimigos (tanto tipo 1, quanto tipo 2) */
	
	private double radius;		// raio (tamanho dos projéteis inimigos)
	
	Eprojectile(){
		super();
		radius = 2.0;
	}
	
	Eprojectile(double x, double y, double vx, double vy){
		super(x, y, vx, vy);
		radius = 2.0;
	}
	
	public double getRadius() {
		return radius;
	}

	void desenha(){
		/* desenhando projeteis (inimigos) */
		GameLib.setColor(Color.RED);
		GameLib.drawCircle(position.x, position.y, radius);
	}
}
