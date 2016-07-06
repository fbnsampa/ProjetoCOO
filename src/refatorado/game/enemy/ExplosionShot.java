package refatorado.game.enemy;

import refatorado.game.Level;
import refatorado.game.projectile.Eprojectile;

class ExplosionShot implements ShotBehavior{

	public void shoot(Enemy caller){
//		position.x += speedy.x * Level.getDelta();
//		position.y += speedy.y * Level.getDelta();

		double v = 1;			//velocidade do tiro
		double vn = v * (-1.0); //velocidade negativa
		double x = caller.getPositionX();
		double y = caller.getPositionY();
		Eprojectile [] novo = new Eprojectile[8];
		
		novo[0] = new Eprojectile (x, y, v, v);
		novo[1] = new Eprojectile (x, y, v, 0.0);
		novo[2] = new Eprojectile (x, y, v, vn );
		novo[3] = new Eprojectile (x, y, 0.0, vn);
		novo[4] = new Eprojectile (x, y, vn, vn);
		novo[5] = new Eprojectile (x, y, vn, 0.0);
		novo[6] = new Eprojectile (x, y, vn, v);
		novo[7] = new Eprojectile (x, y, 0.0, v);
		
		for (int i = 0; i < 8; i++)
			caller.projectiles.add(novo[i]);
	}
	
}
