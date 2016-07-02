package refatorado;
import java.awt.Color;
import refatorado.Player;

public class Main {
	
	/* Constantes relacionadas aos estados que os elementos   */
	/* do jogo (player, projeteis ou inimigos) podem assumir. */
	
	public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;
	public static final int EXPLODING = 2;
	public static long currentTime;
	

	/* Espera, sem fazer nada, at� que o instante de tempo atual seja */
	/* maior ou igual ao instante especificado no par�metro "time.    */
	//teste
	public static void busyWait(long time){
		
		while(System.currentTimeMillis() < time) Thread.yield();
	}
	
	/* Encontra e devolve o primeiro �ndice do  */
	/* array referente a uma posi��o "inativa". */
	
	public static int findFreeIndex(int [] stateArray){
		
		int i;
		
		for(i = 0; i < stateArray.length; i++){
			
			if(stateArray[i] == INACTIVE) break;
		}
		
		return i;
	}
	
	
	/* Encontra e devolve o conjunto de �ndices (a quantidade */
	/* de �ndices � defnida atrav�s do par�metro "amount") do */
	/* array, referentes a posi��es "inativas".               */ 

	public static int [] findFreeIndex(int [] stateArray, int amount){

		int i, k;
		int [] freeArray = { stateArray.length, stateArray.length, stateArray.length };
		
		for(i = 0, k = 0; i < stateArray.length && k < amount; i++){
				
			if(stateArray[i] == INACTIVE) { 
				
				freeArray[k] = i; 
				k++;
			}
		}
		
		return freeArray;
	}
	
	/* M�todo principal */
	
	public static void main(String [] args){

		/* Indica que o jogo est� em execu��o */
		boolean running = true;

		/* vari�veis usadas no controle de tempo efetuado no main loop */
		
		long delta;
		currentTime = System.currentTimeMillis();
		
		Player p = new Player();

		/* vari�veis dos proj�teis disparados pelo player */
		
		int [] projectile_states = new int[10];					// estados
		double [] projectile_X = new double[10];				// coordenadas x
		double [] projectile_Y = new double[10];				// coordenadas y
		double [] projectile_VX = new double[10];				// velocidades no eixo x
		double [] projectile_VY = new double[10];				// velocidades no eixo y

		/* vari�veis dos inimigos tipo 1 */
		
		int [] enemy1_states = new int[10];						// estados
		double [] enemy1_X = new double[10];					// coordenadas x
		double [] enemy1_Y = new double[10];					// coordenadas y
		double [] enemy1_V = new double[10];					// velocidades
		double [] enemy1_angle = new double[10];				// �ngulos (indicam dire��o do movimento)
		double [] enemy1_RV = new double[10];					// velocidades de rota��o
		double [] enemy1_explosion_start = new double[10];		// instantes dos in�cios das explos�es
		double [] enemy1_explosion_end = new double[10];		// instantes dos finais da explos�es
		long [] enemy1_nextShoot = new long[10];				// instantes do pr�ximo tiro
		double enemy1_radius = 9.0;								// raio (tamanho do inimigo 1)
		long nextEnemy1 = currentTime + 2000;					// instante em que um novo inimigo 1 deve aparecer
		
		/* vari�veis dos inimigos tipo 2 */
		
		int [] enemy2_states = new int[10];						// estados
		double [] enemy2_X = new double[10];					// coordenadas x
		double [] enemy2_Y = new double[10];					// coordenadas y
		double [] enemy2_V = new double[10];					// velocidades
		double [] enemy2_angle = new double[10];				// �ngulos (indicam dire��o do movimento)
		double [] enemy2_RV = new double[10];					// velocidades de rota��o
		double [] enemy2_explosion_start = new double[10];		// instantes dos in�cios das explos�es
		double [] enemy2_explosion_end = new double[10];		// instantes dos finais das explos�es
		double enemy2_spawnX = GameLib.WIDTH * 0.20;			// coordenada x do pr�ximo inimigo tipo 2 a aparecer
		int enemy2_count = 0;									// contagem de inimigos tipo 2 (usada na "forma��o de voo")
		double enemy2_radius = 12.0;							// raio (tamanho aproximado do inimigo 2)
		long nextEnemy2 = currentTime + 7000;					// instante em que um novo inimigo 2 deve aparecer
		
		/* vari�veis dos proj�teis lan�ados pelos inimigos (tanto tipo 1, quanto tipo 2) */
		
		int [] e_projectile_states = new int[200];				// estados
		double [] e_projectile_X = new double[200];				// coordenadas x
		double [] e_projectile_Y = new double[200];				// coordenadas y
		double [] e_projectile_VX = new double[200];			// velocidade no eixo x
		double [] e_projectile_VY = new double[200];			// velocidade no eixo y
		double e_projectile_radius = 2.0;						// raio (tamanho dos proj�teis inimigos)
		
		/* estrelas que formam o fundo de primeiro plano */
		
		double [] background1_X = new double[20];
		double [] background1_Y = new double[20];
		double background1_speed = 0.070;
		double background1_count = 0.0;
		
		/* estrelas que formam o fundo de segundo plano */
		
		double [] background2_X = new double[50];
		double [] background2_Y = new double[50];
		double background2_speed = 0.045;
		double background2_count = 0.0;
		
		/* inicializa��es */
		
		for(int i = 0; i < projectile_states.length; i++) projectile_states[i] = INACTIVE;
		for(int i = 0; i < e_projectile_states.length; i++) e_projectile_states[i] = INACTIVE;
		for(int i = 0; i < enemy1_states.length; i++) enemy1_states[i] = INACTIVE;
		for(int i = 0; i < enemy2_states.length; i++) enemy2_states[i] = INACTIVE;
		
		for(int i = 0; i < background1_X.length; i++){
			
			background1_X[i] = Math.random() * GameLib.WIDTH;
			background1_Y[i] = Math.random() * GameLib.HEIGHT;
		}
		
		for(int i = 0; i < background2_X.length; i++){
			
			background2_X[i] = Math.random() * GameLib.WIDTH;
			background2_Y[i] = Math.random() * GameLib.HEIGHT;
		}
						
		/* iniciado interface gr�fica */
		
		GameLib.initGraphics();
		
		/*************************************************************************************************/
		/*                                                                                               */
		/* Main loop do jogo                                                                             */
		/*                                                                                               */
		/* O main loop do jogo possui executa as seguintes opera��es:                                    */
		/*                                                                                               */
		/* 1) Verifica se h� colis�es e atualiza estados dos elementos conforme a necessidade.           */
		/*                                                                                               */
		/* 2) Atualiza estados dos elementos baseados no tempo que correu desde a �ltima atualiza��o     */
		/*    e no timestamp atual: posi��o e orienta��o, execu��o de disparos de proj�teis, etc.        */
		/*                                                                                               */
		/* 3) Processa entrada do usu�rio (teclado) e atualiza estados do player conforme a necessidade. */
		/*                                                                                               */
		/* 4) Desenha a cena, a partir dos estados dos elementos.                                        */
		/*                                                                                               */
		/* 5) Espera um per�odo de tempo (de modo que delta seja aproximadamente sempre constante).      */
		/*                                                                                               */
		/*************************************************************************************************/
		
		while(running){
		
			/* Usada para atualizar o estado dos elementos do jogo    */
			/* (player, proj�teis e inimigos) "delta" indica quantos  */
			/* ms se passaram desde a �ltima atualiza��o.             */
			
			delta = System.currentTimeMillis() - currentTime;
			
			/* J� a vari�vel "currentTime" nos d� o timestamp atual.  */
			
			currentTime = System.currentTimeMillis();
			
			/***************************/
			/* Verifica��o de colis�es */
			/***************************/
						
			if(p.player_state == ACTIVE){
				
				/* colis�es player - projeteis (inimigo) */
				
				for(int i = 0; i < e_projectile_states.length; i++){
					//dx, dy e dist s�o as dist�ncias entre os proj�teis inimigos e
					//o personagem
					double dx = e_projectile_X[i] - p.player_X;
					double dy = e_projectile_Y[i] - p.player_Y;
					double dist = Math.sqrt(dx * dx + dy * dy);
					//aqui se a distancia for menor que a soma do raio do proj�til
					//do inimigo e do jogador, ocorre uma explos�o
					if(dist < (p.player_radius + e_projectile_radius) * 0.8){
						
						p.player_state = EXPLODING;
						p.player_explosion_start = currentTime;
						p.player_explosion_end = currentTime + 2000;
					}
				}
			
				/* colis�es player - inimigos */
							
				for(int i = 0; i < enemy1_states.length; i++){
					//dx, dy e dist s�o a dist�ncia entre o personagem
					//e o inimigo 1
					double dx = enemy1_X[i] - p.player_X;
					double dy = enemy1_Y[i] - p.player_Y;
					double dist = Math.sqrt(dx * dx + dy * dy);
					//aqui � testado se o inimigo1 entrou em colis�o com o jogador
					//caso tenha, ocorre uma explos�o
					if(dist < (p.player_radius + enemy1_radius) * 0.8){
						
						p.player_state = EXPLODING;
						p.player_explosion_start = currentTime;
						p.player_explosion_end = currentTime + 2000;
					}
				}
				
				for(int i = 0; i < enemy2_states.length; i++){
					//dx, dy e dist s�o a dist�ncia entre o personagem
					//e o inimigo 2
					double dx = enemy2_X[i] - p.player_X;
					double dy = enemy2_Y[i] - p.player_Y;
					double dist = Math.sqrt(dx * dx + dy * dy);
					//aqui � testado se o inimigo2 entrou em colis�o com o jogador
					//caso tenha, ocorre uma explos�o					
					if(dist < (p.player_radius + enemy2_radius) * 0.8){
						
						p.player_state = EXPLODING;
						p.player_explosion_start = currentTime;
						p.player_explosion_end = currentTime + 2000;
					}
				}
			}
			
			/* colis�es projeteis (player) - inimigos */
			
			for(int k = 0; k < projectile_states.length; k++){
				
				for(int i = 0; i < enemy1_states.length; i++){
					//se os inimigos1 na tela estiverem ACTIVE ser� feito o c�lculo
					//da dist�ncia entre os proj�teis do jogador e o inimigo1 ativo
					if(enemy1_states[i] == ACTIVE){
					
						double dx = enemy1_X[i] - projectile_X[k];
						double dy = enemy1_Y[i] - projectile_Y[k];
						double dist = Math.sqrt(dx * dx + dy * dy);
						//caso a condi��o seja satisfeita o inimigo1 ser� explodido
						if(dist < enemy1_radius){
							
							enemy1_states[i] = EXPLODING;
							enemy1_explosion_start[i] = currentTime;
							enemy1_explosion_end[i] = currentTime + 500;
						}
					}
				}
				
				for(int i = 0; i < enemy2_states.length; i++){
					//se os inimigos2 na tela estiverem ACTIVE ser� feito o c�lculo
					//da dist�ncia entre os proj�teis do jogador e o inimigo2 ativo					
					if(enemy2_states[i] == ACTIVE){
						
						double dx = enemy2_X[i] - projectile_X[k];
						double dy = enemy2_Y[i] - projectile_Y[k];
						double dist = Math.sqrt(dx * dx + dy * dy);
						//caso a condi��o seja satisfeita o inimigo1 ser� explodido
						if(dist < enemy2_radius){
							
							enemy2_states[i] = EXPLODING;
							enemy2_explosion_start[i] = currentTime;
							enemy2_explosion_end[i] = currentTime + 500;
						}
					}
				}
			}
				
			/***************************/
			/* Atualiza��es de estados */
			/***************************/
			
			/* projeteis (player) */
			
			for(int i = 0; i < projectile_states.length; i++){
				
				if(projectile_states[i] == ACTIVE){
					
					/* verificando se proj�til saiu da tela */
					if(projectile_Y[i] < 0) {
						
						projectile_states[i] = INACTIVE;
					}
					else {
					
						projectile_X[i] += projectile_VX[i] * delta;
						projectile_Y[i] += projectile_VY[i] * delta;
					}
				}
			}
			
			/* projeteis (inimigos) */
			
			for(int i = 0; i < e_projectile_states.length; i++){
				
				if(e_projectile_states[i] == ACTIVE){
					
					/* verificando se proj�til saiu da tela */
					if(e_projectile_Y[i] > GameLib.HEIGHT) {
						
						e_projectile_states[i] = INACTIVE;
					}
					//se o proj�til inimigo n�o sair da tela ele se movimenta
					else {
					
						e_projectile_X[i] += e_projectile_VX[i] * delta;
						e_projectile_Y[i] += e_projectile_VY[i] * delta;
					}
				}
			}
			
			/* inimigos tipo 1 */
			
			for(int i = 0; i < enemy1_states.length; i++){
				//se o inimigo for explodido aquela posi��o do vetor passa a ser inativa
				if(enemy1_states[i] == EXPLODING){
					
					if(currentTime > enemy1_explosion_end[i]){
						
						enemy1_states[i] = INACTIVE;
					}
				}
				
				if(enemy1_states[i] == ACTIVE){
					
					/* verificando se inimigo saiu da tela */
					if(enemy1_Y[i] > GameLib.HEIGHT + 10) {
						//se ele sair da tela a inst�ncia fica como inativa
						//para poder liberar espa�o para os novos inimigos1
						enemy1_states[i] = INACTIVE;
					}
					else {
						//se ele n�o sair da tela, sua posi��o e �ngulo � alterada por meio de um
						//c�lculo de velocidade
						enemy1_X[i] += enemy1_V[i] * Math.cos(enemy1_angle[i]) * delta;
						enemy1_Y[i] += enemy1_V[i] * Math.sin(enemy1_angle[i]) * delta * (-1.0);
						enemy1_angle[i] += enemy1_RV[i] * delta;
						//se o inimigo1 estiver acima do personagem e o tempo atual for maior
						//que o tempo do pr�ximo tiro acontece um disparo
						if(currentTime > enemy1_nextShoot[i] && enemy1_Y[i] < p.player_Y){
							//caso a condi��o seja satisfeita ser� procurado um �ndice de inst�ncia 
							//INACTIVE de proj�til
							int free = findFreeIndex(e_projectile_states);
							//Aqui o proj�til ser� instanciado com o �ndice encontrado
							if(free < e_projectile_states.length){
				
								e_projectile_X[free] = enemy1_X[i];
								e_projectile_Y[free] = enemy1_Y[i];
								e_projectile_VX[free] = Math.cos(enemy1_angle[i]) * 0.45;
								e_projectile_VY[free] = Math.sin(enemy1_angle[i]) * 0.45 * (-1.0);
								e_projectile_states[free] = 1;
								//o tempo do pr�ximo disparo � atualizado
								enemy1_nextShoot[i] = (long) (currentTime + 200 + Math.random() * 500);
							}
						}
					}
				}
			}
			
			/* inimigos tipo 2 */
			
			for(int i = 0; i < enemy2_states.length; i++){
				//se o inimigo for explodido aquela posi��o do vetor passa a ser inativa
				if(enemy2_states[i] == EXPLODING){
					
					if(currentTime > enemy2_explosion_end[i]){
						
						enemy2_states[i] = INACTIVE;
					}
				}
				
				if(enemy2_states[i] == ACTIVE){
					
					/* verificando se inimigo saiu da tela */
					if(	enemy2_X[i] < -10 || enemy2_X[i] > GameLib.WIDTH + 10 ) {
						
						enemy2_states[i] = INACTIVE;
					}
					else {
						
						boolean shootNow = false;//vari�vel que autoriza o uso de projet�is
						double previousY = enemy2_Y[i];
						//caso o inimigo2 esteja ativo s�o feitos os calculos e atribui��es
						//para determinar sua movimenta��o
						enemy2_X[i] += enemy2_V[i] * Math.cos(enemy2_angle[i]) * delta;
						enemy2_Y[i] += enemy2_V[i] * Math.sin(enemy2_angle[i]) * delta * (-1.0);
						enemy2_angle[i] += enemy2_RV[i] * delta;
						
						double threshold = GameLib.HEIGHT * 0.30;
						//dependendo de onde o inimigo 2 estiver na tela a sua rota��o
						//� modificada
						if(previousY < threshold && enemy2_Y[i] >= threshold) {
							
							if(enemy2_X[i] < GameLib.WIDTH / 2) enemy2_RV[i] = 0.003;
							else enemy2_RV[i] = -0.003;
						}
						//aqui dependendo de algumas condi��es � ativada a variav�l
						//shootNow que permite o disparo de proj�teis pelo inimigo2
						if(enemy2_RV[i] > 0 && Math.abs(enemy2_angle[i] - 3 * Math.PI) < 0.05){
							
							enemy2_RV[i] = 0.0;
							enemy2_angle[i] = 3 * Math.PI;
							shootNow = true;
						}
						
						if(enemy2_RV[i] < 0 && Math.abs(enemy2_angle[i]) < 0.05){
							
							enemy2_RV[i] = 0.0;
							enemy2_angle[i] = 0.0;
							shootNow = true;
						}
						//aqui � determinado como os disparos de inimigo2 ser�o feitos						
						if(shootNow){

							double [] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };
							// aqui s�o procuradas inst�ncias livres de proj�teis para serem usadas
							int [] freeArray = findFreeIndex(e_projectile_states, angles.length);

							for(int k = 0; k < freeArray.length; k++){
								
								int free = freeArray[k];
								
								if(free < e_projectile_states.length){
									
									double a = angles[k] + Math.random() * Math.PI/6 - Math.PI/12;
									double vx = Math.cos(a);
									double vy = Math.sin(a);
										
									e_projectile_X[free] = enemy2_X[i];
									e_projectile_Y[free] = enemy2_Y[i];
									e_projectile_VX[free] = vx * 0.30;
									e_projectile_VY[free] = vy * 0.30;
									e_projectile_states[free] = 1;
								}
							}
						}
					}
				}
			}
			
			/* verificando se novos inimigos (tipo 1) devem ser "lan�ados" */
			
			if(currentTime > nextEnemy1){
				
				int free = findFreeIndex(enemy1_states);
				//instancia novos inimigos 1				
				if(free < enemy1_states.length){
					
					enemy1_X[free] = Math.random() * (GameLib.WIDTH - 20.0) + 10.0;
					enemy1_Y[free] = -10.0;
					enemy1_V[free] = 0.20 + Math.random() * 0.15;
					enemy1_angle[free] = 3 * Math.PI / 2;
					enemy1_RV[free] = 0.0;
					enemy1_states[free] = ACTIVE;
					enemy1_nextShoot[free] = currentTime + 500;
					nextEnemy1 = currentTime + 500;
				}
			}
			
			/* verificando se novos inimigos (tipo 2) devem ser "lan�ados" */
			
			if(currentTime > nextEnemy2){
				
				int free = findFreeIndex(enemy2_states);
				//instancia inimigos2				
				if(free < enemy2_states.length){
					
					enemy2_X[free] = enemy2_spawnX;
					enemy2_Y[free] = -10.0;
					enemy2_V[free] = 0.42;
					enemy2_angle[free] = (3 * Math.PI) / 2;
					enemy2_RV[free] = 0.0;
					enemy2_states[free] = ACTIVE;

					enemy2_count++;
					
					if(enemy2_count < 10){
						
						nextEnemy2 = currentTime + 120;
					}
					else {
						
						enemy2_count = 0;
						enemy2_spawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
						nextEnemy2 = (long) (currentTime + 3000 + Math.random() * 3000);
					}
				}
			}
			
			/* Verificando se a explos�o do player j� acabou.         */
			/* Ao final da explos�o, o player volta a ser control�vel */
			if(p.player_state == EXPLODING){
				
				if(currentTime > p.player_explosion_end){
					
					p.player_state = ACTIVE;
				}
			}
			
			/********************************************/
			/* Verificando entrada do usu�rio (teclado) */
			/********************************************/
			
			if(p.player_state == ACTIVE){
				
				if(GameLib.iskeyPressed(GameLib.KEY_UP)) p.player_Y -= delta * p.player_VY;
				if(GameLib.iskeyPressed(GameLib.KEY_DOWN)) p.player_Y += delta * p.player_VY;
				if(GameLib.iskeyPressed(GameLib.KEY_LEFT)) p.player_X -= delta * p.player_VX;
				if(GameLib.iskeyPressed(GameLib.KEY_RIGHT)) p.player_X += delta * p.player_VY;
				if(GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
					
					if(currentTime > p.player_nextShot){
						
						int free = findFreeIndex(projectile_states);
						//aqui s�o instanciados os proj�teis do personagem						
						if(free < projectile_states.length){
							
							projectile_X[free] = p.player_X;
							projectile_Y[free] = p.player_Y - 2 * p.player_radius;
							projectile_VX[free] = 0.0;
							projectile_VY[free] = -1.0;
							projectile_states[free] = 1;
							p.player_nextShot = currentTime + 100;
						}
					}	
				}
			}
			
			if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) running = false;
			
			/* Verificando se coordenadas do player ainda est�o dentro	*/
			/* da tela de jogo ap�s processar entrada do usu�rio.       */
			
			if(p.player_X < 0.0) p.player_X = 0.0;
			if(p.player_X >= GameLib.WIDTH) p.player_X = GameLib.WIDTH - 1;
			if(p.player_Y < 25.0) p.player_Y = 25.0;
			if(p.player_Y >= GameLib.HEIGHT) p.player_Y = GameLib.HEIGHT - 1;

			/*******************/
			/* Desenho da cena */
			/*******************/
			
			/* desenhando plano fundo distante */
			
			GameLib.setColor(Color.DARK_GRAY);
			background2_count += background2_speed * delta;
			
			for(int i = 0; i < background2_X.length; i++){
				
				GameLib.fillRect(background2_X[i], (background2_Y[i] + background2_count) % GameLib.HEIGHT, 2, 2);
			}
			
			/* desenhando plano de fundo pr�ximo */
			
			GameLib.setColor(Color.GRAY);
			background1_count += background1_speed * delta;
			
			for(int i = 0; i < background1_X.length; i++){
				
				GameLib.fillRect(background1_X[i], (background1_Y[i] + background1_count) % GameLib.HEIGHT, 3, 3);
			}
						
			/* desenhando player */
			
			if(p.player_state == EXPLODING){
				
				double alpha = (currentTime - p.player_explosion_start) / (p.player_explosion_end - p.player_explosion_start);
				GameLib.drawExplosion(p.player_X, p.player_Y, alpha);
			}
			else{
				
				GameLib.setColor(Color.BLUE);
				GameLib.drawPlayer(p.player_X, p.player_Y, p.player_radius);
			}
				
			
			/* deenhando projeteis (player) */
			
			for(int i = 0; i < projectile_states.length; i++){
				
				if(projectile_states[i] == ACTIVE){
					
					GameLib.setColor(Color.GREEN);
					GameLib.drawLine(projectile_X[i], projectile_Y[i] - 5, projectile_X[i], projectile_Y[i] + 5);
					GameLib.drawLine(projectile_X[i] - 1, projectile_Y[i] - 3, projectile_X[i] - 1, projectile_Y[i] + 3);
					GameLib.drawLine(projectile_X[i] + 1, projectile_Y[i] - 3, projectile_X[i] + 1, projectile_Y[i] + 3);
				}
			}
			
			/* desenhando projeteis (inimigos) */
		
			for(int i = 0; i < e_projectile_states.length; i++){
				
				if(e_projectile_states[i] == ACTIVE){
	
					GameLib.setColor(Color.RED);
					GameLib.drawCircle(e_projectile_X[i], e_projectile_Y[i], e_projectile_radius);
				}
			}
			
			/* desenhando inimigos (tipo 1) */
			
			for(int i = 0; i < enemy1_states.length; i++){
				
				if(enemy1_states[i] == EXPLODING){
					
					double alpha = (currentTime - enemy1_explosion_start[i]) / (enemy1_explosion_end[i] - enemy1_explosion_start[i]);
					GameLib.drawExplosion(enemy1_X[i], enemy1_Y[i], alpha);
				}
				
				if(enemy1_states[i] == ACTIVE){
			
					GameLib.setColor(Color.CYAN);
					GameLib.drawCircle(enemy1_X[i], enemy1_Y[i], enemy1_radius);
				}
			}
			
			/* desenhando inimigos (tipo 2) */
			
			for(int i = 0; i < enemy2_states.length; i++){
				
				if(enemy2_states[i] == EXPLODING){
					
					double alpha = (currentTime - enemy2_explosion_start[i]) / (enemy2_explosion_end[i] - enemy2_explosion_start[i]);
					GameLib.drawExplosion(enemy2_X[i], enemy2_Y[i], alpha);
				}
				
				if(enemy2_states[i] == ACTIVE){
			
					GameLib.setColor(Color.MAGENTA);
					GameLib.drawDiamond(enemy2_X[i], enemy2_Y[i], enemy2_radius);
				}
			}
			
			/* chamama a display() da classe GameLib atualiza o desenho exibido pela interface do jogo. */
			
			GameLib.display();
			
			/* faz uma pausa de modo que cada execu��o do la�o do main loop demore aproximadamente 5 ms. */
			
			busyWait(currentTime + 5);
		}
		
		System.exit(0);
	}
	
}

