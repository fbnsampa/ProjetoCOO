package refatorado.game.projectile;

import refatorado.game.Cordinate;
import refatorado.game.Level;
import refatorado.game.Observer;

abstract class Projectile {
	protected Cordinate position;
	private Cordinate speed;

	Projectile(){
		position = new Cordinate();
		speed = new Cordinate();
	}
	
	Projectile(double x, double y, double vx, double vy){
		position = new Cordinate(x, y);
		speed = new Cordinate(vx, vy);
	}
	
	public double getPositionX() {
		return position.x;
	}

	public double getPositionY() {
		return position.y;
	}

	public void update(){
		position.x += speed.x * Level.getDelta();
		position.y += speed.y * Level.getDelta();
	}
}
