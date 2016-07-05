package refatorado;
import java.awt.Color;

public class Ship extends Enemy implements EnemyInterface  {
	
	long nextShoot;			// instantes do pr�ximo tiro
	
	public Ship (){
		super();
		//int size = 10; 
		//states = new int[size];
//		V = new double[size];
//		RV = new double[size];
//		explosion_start = new double[size];
//		explosion_end = new double[size];
//		nextShoot = new long[size];
		radius = 9.0;
		next = Main.currentTime + 2000;
		//position = new Cordinate [size];
		//for (int i = 0; i < size; i++) position[i] = new Cordinate();
	}
	
	public void atualiza(Eprojectile eprojectile, Player player){
		/* inimigos tipo 1 */
		
		//for(int i = 0; i < states.length; i++){
		
			
			//se o inimigo for explodido aquela posi��o do vetor passa a ser inativa
			if(states[i] == Main.EXPLODING){
				
				if(Main.currentTime > explosion_end){
					
					states[i] = Main.INACTIVE;
				}
			}
			
			if(states[i] == Main.ACTIVE){
				
				/* verificando se inimigo saiu da tela */
				if(position.y > GameLib.HEIGHT + 10) {
					//se ele sair da tela a inst�ncia fica como inativa
					//para poder liberar espa�o para os novos inimigos1
					states[i] = Main.INACTIVE;
				} else {
					//se ele n�o sair da tela, sua posi��o e �ngulo � alterada por meio de um
					//c�lculo de velocidade
					position.x += V * Math.cos(position.angle) * Main.delta;
					position.y += V * Math.sin(position.angle) * Main.delta * (-1.0);
					position.angle += RV * Main.delta;
					double X = position.x;
					double Y = position.y;
					double angle = position.angle;
					
					//se o inimigo1 estiver acima do personagem e o tempo atual for maior
					//que o tempo do pr�ximo tiro acontece um disparo
					if(Main.currentTime > nextShoot && Y < player.position.y){
						//caso a condi��o seja satisfeita ser� procurado um �ndice de inst�ncia 
						//INACTIVE de proj�til
						//int free = Main.findFreeIndex(eprojectile.states);
						//Aqui o proj�til ser� instanciado com o �ndice encontrado
						//if(free < eprojectile.states.length){
			
							eprojectile.position.x = X;
							eprojectile.position.y = Y;
							eprojectile.speedy.x = Math.cos(angle) * 0.45;
							eprojectile.speedy.y = Math.sin(angle) * 0.45 * (-1.0);
							//eprojectile.states[free] = 1;
							//o tempo do pr�ximo disparo � atualizado
							nextShoot = (long) (Main.currentTime + 200 + Math.random() * 500);
						//}
					}
				}
			}
		//}
	}
	
	public void verifica(){
		/* verificando se novos inimigos (tipo 1) devem ser "lan�ados" */
		
		if(Main.currentTime > next){
			
			//int free = Main.findFreeIndex(states);
			//instancia novos inimigos 1				
			//if(free < states.length){
				
				position.x = Math.random() * (GameLib.WIDTH - 20.0) + 10.0;
				position.y = -10.0;
				V = 0.20 + Math.random() * 0.15;
				position.angle = 3 * Math.PI / 2;
				RV = 0.0;
				//states[free] = Main.ACTIVE;
				nextShoot = Main.currentTime + 500;
				next = Main.currentTime + 500;
			//}
		}
	}
	
	public void desenha(){
		/* desenhando inimigos (tipo 1) */
		
		//for(int i = 0; i < states.length; i++){
			
			if(states[i] == Main.EXPLODING){
				
				double alpha = (Main.currentTime - explosion_start) / (explosion_end - explosion_start);
				GameLib.drawExplosion(position.x, position.y, alpha);
			}
			
			if(states[i] == Main.ACTIVE){
		
				GameLib.setColor(Color.CYAN);
				GameLib.drawCircle(position.x, position.y, radius);
			}
		//}
	}

}
