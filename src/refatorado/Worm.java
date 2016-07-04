package refatorado;
import java.awt.Color;


public class Worm extends Enemy implements EnemyInterface {
/* variáveis dos inimigos tipo 2 */
	
	double spawnX;				// coordenada x do próximo inimigo tipo 2 a aparecer
	int count;					// contagem de inimigos tipo 2 (usada na "formação de voo")

	public Worm(){
		super(10);
		int size = 10;
		states = new int[size];
		V = new double[size];
		RV = new double[size];
		explosion_start = new double[size];
		explosion_end = new double[size];
		spawnX = GameLib.WIDTH * 0.20;
		count = 0;
		radius = 12.0;
		next = Main.currentTime + 7000;
		position = new Cordinate [size];
		for (int i = 0; i < size; i++) position[i] = new Cordinate();
	}
	
	public void atualiza(Eprojectile eprojectile){
		/* inimigos tipo 2 */
		
		for(int i = 0; i < states.length; i++){
			//se o inimigo for explodido aquela posição do vetor passa a ser inativa
			if(states[i] == Main.EXPLODING){
				
				if(Main.currentTime > explosion_end[i]){
					
					states[i] = Main.INACTIVE;
				}
			}
			
			if(states[i] == Main.ACTIVE){
				
				/* verificando se inimigo saiu da tela */
				if(position[i].x < -10 || position[i].x > GameLib.WIDTH + 10 ) {
					states[i] = Main.INACTIVE;
				}
				else {
					boolean shootNow = false;//variável que autoriza o uso de projetéis
					double previousY = position[i].y;
					//caso o inimigo2 esteja ativo são feitos os calculos e atribuições
					//para determinar sua movimentação
					
					position[i].x += V[i] * Math.cos(position[i].angle) * Main.delta;
					position[i].y += V[i] * Math.sin(position[i].angle) * Main.delta * (-1.0);
					position[i].angle += RV[i] * Main.delta;
					
					double threshold = GameLib.HEIGHT * 0.30;
					//dependendo de onde o inimigo 2 estiver na tela a sua rotação
					//é modificada
					if(previousY < threshold && position[i].y >= threshold) {
						
						if(position[i].x < GameLib.WIDTH / 2) RV[i] = 0.003;
						else RV[i] = -0.003;
					}
					//aqui dependendo de algumas condições é ativada a variavél
					//shootNow que permite o disparo de projéteis pelo inimigo2
					if(RV[i] > 0 && Math.abs(position[i].angle - 3 * Math.PI) < 0.05){
						
						RV[i] = 0.0;
						position[i].angle = 3 * Math.PI;
						shootNow = true;
					}
					
					if(RV[i] < 0 && Math.abs(position[i].angle) < 0.05){
						
						RV[i] = 0.0;
						position[i].angle = 0.0;
						shootNow = true;
					}
					//aqui é determinado como os disparos de inimigo2 serão feitos						
					if(shootNow){

						double [] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };
						// aqui são procuradas instâncias livres de projéteis para serem usadas
						int [] freeArray = Main.findFreeIndex(eprojectile.states, angles.length);

						for(int k = 0; k < freeArray.length; k++){
							
							int free = freeArray[k];
							
							if(free < eprojectile.states.length){
								
								double a = angles[k] + Math.random() * Math.PI/6 - Math.PI/12;
								double vx = Math.cos(a);
								double vy = Math.sin(a);
									
								eprojectile.position[free].x = position[i].x;
								eprojectile.position[free].y = position[i].y;
								eprojectile.speedy[free].x = vx * 0.30;
								eprojectile.speedy[free].y = vy * 0.30;
								eprojectile.states[free] = 1;
							}
						}
					}
				}
			}
		}
	}
	
	public void verifica(){
		/* verificando se novos inimigos (tipo 2) devem ser "lançados" */
		
		if(Main.currentTime > next){
			
			int free = Main.findFreeIndex(states);
			//instancia inimigos2				
			if(free < states.length){
				
				position[free].x = spawnX;
				position[free].y = -10.0;
				V[free] = 0.42;
				position[free].angle = (3 * Math.PI) / 2;
				RV[free] = 0.0;
				states[free] = Main.ACTIVE;

				count++;
				
				if(count < 10){
					
					next = Main.currentTime + 120;
				}
				else {
					
					count = 0;
					spawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
					next = (long) (Main.currentTime + 3000 + Math.random() * 3000);
				}
			}
		}
	}
	
	public void desenha(){
		/* desenhando inimigos (tipo 2) */
		
		for(int i = 0; i < states.length; i++){
			
			if(states[i] == Main.EXPLODING){
				
				double alpha = (Main.currentTime - explosion_start[i]) / (explosion_end[i] - explosion_start[i]);
				GameLib.drawExplosion(position[i].x, position[i].y, alpha);
			}
			
			if(states[i] == Main.ACTIVE){
		
				GameLib.setColor(Color.MAGENTA);
				GameLib.drawDiamond(position[i].x, position[i].y, radius);
			}
		}
	}

}
