package refatorado.game.enemy;

import original.GameLib;
import refatorado.game.Level;

class WaveMove implements MoveBehavior{
	double upThreshold;
	double bottomThreshold;
	
	public WaveMove(){
		this.upThreshold = 0;
		this.bottomThreshold = 200;
	}
	
	public WaveMove(Enemy caller, int dist){
		upThreshold = caller.getPositionY() - dist;
		bottomThreshold = caller.getPositionY() + dist;
	}
	
	
	public void move(Enemy caller){
		caller.position.x += caller.getSpeedX() * Level.getDelta();
		
		caller.position.y += caller.getSpeedY() * Level.getDelta();
		
		
		
		if (caller.position.x <= caller.radius || caller.position.x >= GameLib.WIDTH - caller.radius) caller.speed.x *= -1.0;
		if (caller.position.y <= upThreshold || caller.position.y >= bottomThreshold) caller.speed.y *= -1.0;
	}

}
