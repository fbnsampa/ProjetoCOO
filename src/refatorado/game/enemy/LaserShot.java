package refatorado.game.enemy;

import refatorado.game.projectile.Eprojectile;

class LaserShot implements ShotBehavior{

	public void shoot(Enemy caller){
		double x = caller.getPositionX();
		double y = caller.getPositionY();
		double v = Math.abs(caller.getSpeedY()) + 0.6;
		int dist = 0;
		
		for (int i = 0; i < 30; i++){
			dist = i * 3;
			caller.projectiles.add(new Eprojectile(x, y + dist, 0.0, v));
			caller.projectiles.add(new Eprojectile(x + i + 10, y + dist, v/8, v));
			caller.projectiles.add(new Eprojectile(x - i - 10, y + dist, (v/8) * -1, v));
		}
		
	}
	
}