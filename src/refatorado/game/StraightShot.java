package refatorado.game;

class StraightShot implements ShotBehavior {
	
	public void shoot(Enemy caller){
		double vx = Math.cos(caller.position.angle) * 0.45;
		double vy = Math.sin(caller.position.angle) * 0.45 * (-1.0);
		Eprojectile novo = new Eprojectile(caller.getPositionX(), caller.getPositionY(), vx, vy);
		caller.projectiles.add(novo);
	}
}
