package refatorado.game.projectile;
import java.awt.Color;

import refatorado.gamelib.GameLib;

public class Eprojectile extends Projectile {
	/* vari�veis dos proj�teis lan�ados pelos inimigos (tanto tipo 1, quanto tipo 2) */
	
	private double radius;		// raio (tamanho dos proj�teis inimigos)
	
	public Eprojectile(){
		super();
		radius = 2.0;
	}
	
	public Eprojectile(double x, double y, double vx, double vy){
		super(x, y, vx, vy);
		radius = 2.0;
	}
	
	public double getRadius() {
		return radius;
	}

	public void desenha(){
		/* desenhando projeteis (inimigos) */
		GameLib.setColor(Color.RED);
		GameLib.drawCircle(position.x, position.y, radius);
	}
}
