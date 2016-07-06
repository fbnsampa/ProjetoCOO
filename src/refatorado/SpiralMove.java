package refatorado;

public class SpiralMove implements MoveBehavior{

	public void move(Enemy caller){
		double previousY = caller.position.y;
		//caso o inimigo esteja ativo são feitos os calculos e atribuições
		//para determinar sua movimentação
		caller.position.x += caller.V * Math.cos(caller.position.angle) * Level.getDelta();
		caller.position.y += caller.V * Math.sin(caller.position.angle) * Level.getDelta() * (-1.0);
		caller.position.angle += caller.RV * Level.getDelta();
		
		double threshold = GameLib.HEIGHT * 0.30;
		//dependendo de onde o inimigo 2 estiver na tela a sua rotação
		//é modificada
		if(previousY < threshold && caller.position.y >= threshold) {
			if(caller.position.x < GameLib.WIDTH / 2) caller.RV = 0.003;
			else caller.RV = -0.003;
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
