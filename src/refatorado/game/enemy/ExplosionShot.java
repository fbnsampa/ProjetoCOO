package refatorado.game.enemy;

import refatorado.game.Level;
import refatorado.game.projectile.Eprojectile;

class ExplosionShot implements ShotBehavior{

	public void shoot(Enemy caller){
//		position.x += speedy.x * Level.getDelta();
//		position.y += speedy.y * Level.getDelta();
//		double maxSpeed = Math.max(caller.getSpeedX(), caller.getSpeedY());
//		double v = maxSpeed + 0.2;			//velocidade do tiro
//		double vn = v * (-1.0); 			//velocidade negativa
		
		double speed = 0.05;

		double x = caller.getPositionX();
		double y = caller.getPositionY();
		double vx = caller.getSpeedX();
		double vy = caller.getSpeedY();
		double dx = caller.getDirectionX();
		double dy = caller.getDirectionY();
//		double vx = caller.getSpeedX() * caller.getDirectionX() + speed;
//		double vy = caller.getSpeedY() * caller.getDirectionY() + speed;
//		double vxr = vx * -1;
//		double vyr = vy * -1;

//		double vx = speed;
//		double vy = speed;
//		double vxr = vx * -1;
//		double vyr = vy * -1;
		
		Eprojectile [] novo = new Eprojectile[8];
		double [][] ref = {{0.0, -1.0},{1.0, -1.0},{1.0, 0.0},{1.0, 1.0},{0.0, 1.0},{-1.0, 1.0},{-1.0, 0.0},{-1.0, -1.0}};
		
		for (int i = 0; i < 8; i ++){
			double tx, ty;
			
			if (ref[i][0] == dx) tx = (speed + vx) * ref[i][0];
			else tx = (speed - vx) * ref[i][0];
			
			if (ref[i][1] == dy) ty = (speed + vy) * ref[i][1];
			else ty = (speed - vy) * ref[i][1];
				
			novo[i] = new Eprojectile(x, y, tx, ty);	
				
		}
		
//		novo[0] = new Eprojectile (x, y, 0.0, vyr);
//		novo[1] = new Eprojectile (x, y, vx, vyr);
//		novo[2] = new Eprojectile (x, y, vx, 0.0 );
//		novo[3] = new Eprojectile (x, y, vx, vy);
//		novo[4] = new Eprojectile (x, y, 0.0, vy);
//		novo[5] = new Eprojectile (x, y, vxr, vy);
//		novo[6] = new Eprojectile (x, y, vxr, 0.0);
//		novo[7] = new Eprojectile (x, y, vxr, vyr);
//		
		for (int i = 0; i < 8; i++)
			caller.projectiles.add(novo[i]);
	}
	
}
