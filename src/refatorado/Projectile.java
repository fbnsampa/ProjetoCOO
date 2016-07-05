package refatorado;

abstract class Projectile implements Observer {
	Cordinate position;
	Cordinate speedy;

	Projectile(){
		position = new Cordinate();
		speedy = new Cordinate();
	}
	
	Projectile(double x, double y, double vx, double vy){
		position = new Cordinate(x, y);
		speedy = new Cordinate(vx, vy);
	}
	
	public void atualiza(){
		position.x += speedy.x * Level.delta;
		position.y += speedy.y * Level.delta;
	}
}
