package refatorado.game.enemy;

import refatorado.game.Level;
import refatorado.gamelib.GameLib;

class SpiralMove implements MoveBehavior{

	public void move(Enemy caller){
		double previousY = caller.position.y;
		//caso o inimigo esteja ativo são feitos os calculos e atribuições
		//para determinar sua movimentação
		caller.position.x += caller.getSpeedX() * Math.cos(caller.position.angle) * Level.getDelta();
		caller.position.y += caller.getSpeedY() * Math.sin(caller.position.angle) * Level.getDelta() * (-1.0);
		caller.position.angle += caller.RV * Level.getDelta();
		double threshold;

		if(previousY<216) threshold = GameLib.HEIGHT * 0.3;
		else if(previousY<432) threshold = GameLib.HEIGHT * 0.6; 
			else if(previousY<648) threshold = GameLib.HEIGHT * 0.9;
				else threshold = GameLib.HEIGHT;

		if(previousY < threshold && caller.position.y >= threshold) {
			if(caller.position.x < GameLib.WIDTH / 2) caller.RV = 0.003;//começa girando pra direita
			else caller.RV = -0.003;//começa girando pra esquerda
		}
		
		if(caller.RV > 0 && Math.abs(caller.position.angle - 3 * Math.PI) < 0.05){
			caller.RV = 0.0;
			caller.position.angle = 3 * Math.PI;
		}
		
		if(caller.RV < 0 && Math.abs(caller.position.angle) < 0.05){
			caller.RV = 0.0;
			caller.position.angle = 0.0;
		}
	}
	
}
