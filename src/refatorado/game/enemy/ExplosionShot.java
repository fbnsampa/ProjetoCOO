package refatorado.game.enemy;

import original.GameLib;
import refatorado.game.Level;
import refatorado.game.projectile.Eprojectile;

class ExplosionShot implements ShotBehavior{

	public void shoot(Enemy caller){
		
		double v = 0.35;
		double vn = v * -1.0;
		double x = caller.getPositionX();
		double y = caller.getPositionY();
		
		caller.projectiles.add(new Eprojectile(x, y, 0.0, vn));
		caller.projectiles.add(new Eprojectile(x, y, v, vn));
		caller.projectiles.add(new Eprojectile(x, y, v, 0.0));
		caller.projectiles.add(new Eprojectile(x, y, v, v));
		caller.projectiles.add(new Eprojectile(x, y, 0.0, v));
		caller.projectiles.add(new Eprojectile(x, y, vn, v));
		caller.projectiles.add(new Eprojectile(x, y, vn, 0.0));
		caller.projectiles.add(new Eprojectile(x, y, vn, vn));
		
		
		
	}
	
}
