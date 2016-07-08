package refatorado.game.enemy;

import refatorado.game.projectile.Eprojectile;

class StraightShot implements ShotBehavior {
	
	public void shoot(Enemy caller){
		double vx = caller.getSpeedX() + 0.2 * caller.getDirectionX();
		double vy = caller.getSpeedY() + 0.2 * caller.getDirectionY();
		
		Eprojectile novo = new Eprojectile(caller.getPositionX(), caller.getPositionY(), vx, vy);
		
		caller.projectiles.add(novo);
	}
}
