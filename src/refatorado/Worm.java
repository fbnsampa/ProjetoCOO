package refatorado;
import java.awt.Color;


public class Worm extends Enemy implements EnemyInterface {
/* variáveis dos inimigos tipo 2 */
	
	double spawnX;				// coordenada x do próximo inimigo tipo 2 a aparecer
	int count;					// contagem de inimigos tipo 2 (usada na "formação de voo")

	public Worm(){
		super();
//		int size = 10;
//		states = new int[size];
//		V = new double[size];
//		RV = new double[size];
//		explosion_start = new double[size];
//		explosion_end = new double[size];
		spawnX = GameLib.WIDTH * 0.20;
		count = 0;
		radius = 12.0;
		next = Main.currentTime + 7000;
		//position = new Cordinate [size];
		//for (int i = 0; i < size; i++) position[i] = new Cordinate();
	}
	
	public void atualiza(Eprojectile eprojectile){
		/* inimigos tipo 2 */
		
		//for(int i = 0; i < states.length; i++){
			//se o inimigo for explodido aquela posição do vetor passa a ser inativa
			if(states[i] == Main.EXPLODING){
				
				if(Main.currentTime > explosion_end){
					
					states[i] = Main.INACTIVE;
				}
			}
			
			if(states[i] == Main.ACTIVE){
				
				/* verificando se inimigo saiu da tela */
				if(position.x < -10 || position.x > GameLib.WIDTH + 10 ) {
					states[i] = Main.INACTIVE;
				}
				else {
					boolean shootNow = false;//variável que autoriza o uso de projetéis
					double previousY = position.y;
					//caso o inimigo2 esteja ativo são feitos os calculos e atribuições
					//para determinar sua movimentação
					
					position.x += V * Math.cos(position.angle) * Main.delta;
					position.y += V * Math.sin(position.angle) * Main.delta * (-1.0);
					position.angle += RV * Main.delta;
					
					double threshold = GameLib.HEIGHT * 0.30;
					//dependendo de onde o inimigo 2 estiver na tela a sua rotação
					//é modificada
					if(previousY < threshold && position.y >= threshold) {
						
						if(position.x < GameLib.WIDTH / 2) RV = 0.003;
						else RV = -0.003;
					}
					//aqui dependendo de algumas condições é ativada a variavél
					//shootNow que permite o disparo de projéteis pelo inimigo2
					if(RV > 0 && Math.abs(position.angle - 3 * Math.PI) < 0.05){
						
						RV = 0.0;
						position.angle = 3 * Math.PI;
						shootNow = true;
					}
					
					if(RV < 0 && Math.abs(position.angle) < 0.05){
						
						RV = 0.0;
						position.angle = 0.0;
						shootNow = true;
					}
					//aqui é determinado como os disparos de inimigo2 serão feitos						
					if(shootNow){

						double [] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };
						// aqui são procuradas instâncias livres de projéteis para serem usadas
						//int [] freeArray = Main.findFreeIndex(eprojectile.states, angles.length);

						for(int k = 0; k < 3; k++){
							
							//int free = freeArray[k];
							
							//if(free < eprojectile.states.length){
								
								double a = angles[k] + Math.random() * Math.PI/6 - Math.PI/12;
								double vx = Math.cos(a);
								double vy = Math.sin(a);
									
								eprojectile.position.x = position.x;
								eprojectile.position.y = position.y;
								eprojectile.speedy.x = vx * 0.30;
								eprojectile.speedy.y = vy * 0.30;
								//eprojectile.states[free] = 1;
							//}
						}
					}
				}
			}
		//}
	}
	
	public void verifica(){
		/* verificando se novos inimigos (tipo 2) devem ser "lançados" */
		
		if(Main.currentTime > next){
			
			//int free = Main.findFreeIndex(states);
			//instancia inimigos2				
			//if(free < states.length){
				
				position.x = spawnX;
				position.y = -10.0;
				V = 0.42;
				position.angle = (3 * Math.PI) / 2;
				RV = 0.0;
				//states[free] = Main.ACTIVE;

				count++;
				
				if(count < 10){
					
					next = Main.currentTime + 120;
				}
				else {
					
					count = 0;
					spawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
					next = (long) (Main.currentTime + 3000 + Math.random() * 3000);
				}
			//}
		}
	}
	
	public void desenha(){
		/* desenhando inimigos (tipo 2) */
		
		//for(int i = 0; i < states.length; i++){
			
			if(states[i] == Main.EXPLODING){
				
				double alpha = (Main.currentTime - explosion_start) / (explosion_end - explosion_start);
				GameLib.drawExplosion(position.x, position.y, alpha);
			}
			
			if(states[i] == Main.ACTIVE){
		
				GameLib.setColor(Color.MAGENTA);
				GameLib.drawDiamond(position.x, position.y, radius);
			}
		//}
	}

}
