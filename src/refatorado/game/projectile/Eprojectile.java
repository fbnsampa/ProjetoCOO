package refatorado.game.projectile;
import java.awt.Color;

import refatorado.gamelib.GameLib;

public class Eprojectile extends Projectile {
	private double radius;		// raio (tamanho dos projéteis inimigos)
	
	public Eprojectile(double x, double y, double vx, double vy){
		super(x, y, vx, vy);
		radius = 2.0;
	}
	
	public double getRadius() {
		return radius;
	}

	public void draw(){
		/* desenhando projeteis (inimigos) */
		GameLib.setColor(Color.RED);
		GameLib.drawCircle(position.x, position.y, radius);
	}
}
