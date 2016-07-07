package refatorado.game.enemy;

import original.GameLib;
import refatorado.game.Level;

class PongMove implements MoveBehavior{
	
	public void move(Enemy caller){
		caller.position.x += caller.getSpeedX() * Level.getDelta();
		caller.position.y += caller.getSpeedY() * Level.getDelta();
		
		if (caller.position.x <= caller.radius || caller.position.x >= GameLib.WIDTH - caller.radius) caller.speed.x *= -1.0;
		if (caller.position.y <= caller.radius || caller.position.y >= GameLib.HEIGHT - caller.radius) caller.speed.y *= -1.0;
	}
	
}
