package refatorado.game.projectile;

import refatorado.game.Cordinate;
import refatorado.game.Level;
import refatorado.game.Observer;

abstract class Projectile implements Observer {
	protected Cordinate position;
	private Cordinate speedy;

	Projectile(){
		position = new Cordinate();
		speedy = new Cordinate();
	}
	
	public double getPositionX() {
		return position.x;
	}

	public double getPositionY() {
		return position.y;
	}

	Projectile(double x, double y, double vx, double vy){
		position = new Cordinate(x, y);
		speedy = new Cordinate(vx, vy);
	}
	
	public void atualiza(){
		position.x += speedy.x * Level.getDelta();
		position.y += speedy.y * Level.getDelta();
	}
}
