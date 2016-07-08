package refatorado.game.enemy;

import refatorado.game.Level;

class StraightMove implements MoveBehavior{

	public void move(Enemy caller){
		caller.position.x += caller.getSpeedX() * Level.getDelta();
		caller.position.y += caller.getSpeedY() * Level.getDelta();
	}
	
}
