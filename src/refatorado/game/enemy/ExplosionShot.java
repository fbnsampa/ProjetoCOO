package refatorado.game.enemy;

import refatorado.game.Level;
import refatorado.game.projectile.Eprojectile;

class ExplosionShot implements ShotBehavior{

	public void shoot(Enemy caller){
		
		double speed = 0.05;

		double x = caller.getPositionX();
		double y = caller.getPositionY();
		double vx = caller.getSpeedX();
		double vy = caller.getSpeedY();
		double dx = caller.getDirectionX();
		double dy = caller.getDirectionY();
		Eprojectile [] novo = new Eprojectile[8];
		double [][] ref = {{0.0, -1.0},{1.0, -1.0},{1.0, 0.0},{1.0, 1.0},{0.0, 1.0},{-1.0, 1.0},{-1.0, 0.0},{-1.0, -1.0}};
		
		System.out.println((vx + speed) * -1.0);
		System.out.println(speed * -1.0);
		System.out.println(vx - speed);
		System.out.println(vx  * -1.0);
		System.out.println("0.0");
		System.out.println(vx);
		System.out.println((vx - speed) * -1.0);
		System.out.println(speed);
		System.out.println(vx + speed);
		
		
		
		for (int i = 0; i < 8; i ++){
			double tx = 0.0;
			double ty = 0.0;
			
//			if (ref[i][0] == -1.0){
//				if (dx == -1.0) tx = (vx + speed) * -1.0; 
//				if (dx == 0.0) tx = speed * -1.0; 
//				if (dx == 1.0) tx = vx - speed; 
//			}
//			
//			else if (ref[i][0] == 0.0){
//				if (dx == -1.0) tx = vx  * -1.0; 
//				if (dx == 0.0) tx = 0.0; 
//				if (dx == 1.0) tx = vx; 
//			}
//			
//			else if (ref[i][0] == 1.0){
//				if (dx == -1.0) tx = (vx - speed) * -1.0; 
//				if (dx == 0.0) tx = speed; 
//				if (dx == 1.0) tx = vx + speed; 
//			}
//			
//			if (ref[i][1] == -1.0){
//				if (dy == -1.0) ty = (vy + speed) * -1.0; 
//				if (dy == 0.0) ty = speed * -1.0; 
//				if (dy == 1.0) ty = vy - speed; 
//			}
//			
//			else if (ref[i][1] == 0.0){
//				if (dy == -1.0) ty = vy  * -1.0; 
//				if (dy == 0.0) ty = 0.0; 
//				if (dy == 1.0) ty = vy; 
//			}
//			
//			else if (ref[i][1] == 1.0){
//				if (dy == -1.0) ty = (vy - speed) * -1.0; 
//				if (dy == 0.0) ty = speed; 
//				if (dy == 1.0) ty = vy + speed; 
//			}
	
			
			
			if (ref[i][0] == -1.0){
				if (dx == -1.0) tx = -0.25; //(vy + speed) * -1.0;
				if (dx == 0.0) tx = -0.05; //speed * -1.0;
				if (dx == 1.0) tx = 0.15; //(vy - speed);
			}
			
			else if (ref[i][0] == 0.0){
				if (dx == -1.0) tx = -0.2; //tx = speed * -1;
				if (dx == 0.0) tx = 0.0; //tx = 0.0;
				if (dx == 1.0) tx = 0.2; //tx = vy;
			}
			
			else if (ref[i][0] == 1.0){
				if (dx == -1.0) tx = -0.15; //(vy - speed) * -1.0;
				if (dx == 0.0) tx = 0.05; //speed;
				if (dx == 1.0) tx = 0.25; //(vy + speed);
			}
			
			if (ref[i][1] == -1.0){
				if (dy == -1.0) ty = -0.25; //(vy + speed) * -1.0;
				if (dy == 0.0) ty = -0.05; //speed * -1.0;
				if (dy == 1.0) ty = 0.15; //(vy - speed);
			}
			
			else if (ref[i][1] == 0.0){
				if (dy == -1.0) ty = -0.2; //ty = speed * -1;
				if (dy == 0.0) ty = 0.0; //ty = 0.0;
				if (dy == 1.0) ty = 0.2; //ty = vy;
			}
			
			else if (ref[i][1] == 1.0){
				if (dy == -1.0) ty = -0.15; //(vy - speed) * -1.0;
				if (dy == 0.0) ty = 0.05; //speed;
				if (dy == 1.0) ty = 0.25; //(vy + speed);
			}
			
			novo[i] = new Eprojectile(x, y, tx, ty);	
		}
		
		for (int i = 0; i < 8; i++)
			caller.projectiles.add(novo[i]);
	}
	
}
