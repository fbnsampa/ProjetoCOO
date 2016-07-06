package refatorado.game;

class StraightMove implements MoveBehavior{

	public void move(Enemy caller){
		caller.position.x += caller.V * Math.cos(caller.position.angle) * Level.getDelta();
		caller.position.y += caller.V * Math.sin(caller.position.angle) * Level.getDelta() * (-1.0);
		caller.position.angle += caller.RV * Level.getDelta();
	}
	
}
