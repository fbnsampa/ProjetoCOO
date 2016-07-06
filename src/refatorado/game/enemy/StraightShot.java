package refatorado.game.enemy;

import refatorado.game.projectile.Eprojectile;

class StraightShot implements ShotBehavior {
	
	public void shoot(Enemy caller){
		double vx = caller.getSpeedX() + 0.2 * getDirection(caller.getSpeedX());
		double vy = caller.getSpeedY() + 0.2 * getDirection(caller.getSpeedY());
		
		Eprojectile novo = new Eprojectile(caller.getPositionX(), caller.getPositionY(), vx, vy);
		caller.projectiles.add(novo);
	}
	
	public double getDirection(double x){
		if (x > 0) return 1.0;
		if (x < 0) return -1.0;
		return 0.0;
	}
}
