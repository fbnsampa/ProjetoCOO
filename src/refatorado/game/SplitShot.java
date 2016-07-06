package refatorado.game;

class SplitShot implements ShotBehavior{
	
	public void shoot(Enemy caller){
		double [] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };
		for(int k = 0; k < 3; k++){
			double a = angles[k] + Math.random() * Math.PI/6 - Math.PI/12;
			double vx = Math.cos(a) * 0.30;
			double vy = Math.sin(a) * 0.30;
			Eprojectile novo  = new Eprojectile(caller.getPositionX(), caller.getPositionY(), vx, vy);
			caller.projectiles.add(novo);
		}
	}
	
}
