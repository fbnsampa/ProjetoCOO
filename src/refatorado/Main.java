package refatorado;
import refatorado.Player;

public class Main {
	
	/* Constantes relacionadas aos estados que os elementos   */
	/* do jogo (player, projeteis ou inimigos) podem assumir. */
	
	public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;
	public static final int EXPLODING = 2;
	
	/* variáveis usadas no controle de tempo efetuado no main loop */
	static long currentTime;
	static long delta;
	
	/* Indica que o jogo está em execução */
	static boolean running;

	/* Espera, sem fazer nada, até que o instante de tempo atual seja */
	/* maior ou igual ao instante especificado no parâmetro "time.    */
	//teste
	public static void busyWait(long time){
		
		while(System.currentTimeMillis() < time) Thread.yield();
	}
	
	/* Encontra e devolve o primeiro índice do  */
	/* array referente a uma posição "inativa". */
	
	public static int findFreeIndex(int [] stateArray){
		
		int i;
		
		for(i = 0; i < stateArray.length; i++){
			
			if(stateArray[i] == INACTIVE) break;
		}
		
		return i;
	}
	
	
	/* Encontra e devolve o conjunto de índices (a quantidade */
	/* de índices é defnida através do parâmetro "amount") do */
	/* array, referentes a posições "inativas".               */ 

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
	
	/* Método principal */
	
	public static void main(String [] args){

		/* Indica que o jogo está em execução */
		running = true;

		/* variáveis usadas no controle de tempo efetuado no main loop */
		
		currentTime = System.currentTimeMillis();
		
		Player player = new Player();
		Pprojectile pprojectile = new Pprojectile();
		Ship ship = new Ship();
		Worm worm = new Worm();
		Eprojectile eprojectile = new Eprojectile();
		Background background = new Background();
						
		/* iniciado interface gráfica */
		
		GameLib.initGraphics();
		
		/*************************************************************************************************/
		/*                                                                                               */
		/* Main loop do jogo                                                                             */
		/*                                                                                               */
		/* O main loop do jogo possui executa as seguintes operações:                                    */
		/*                                                                                               */
		/* 1) Verifica se há colisões e atualiza estados dos elementos conforme a necessidade.           */
		/*                                                                                               */
		/* 2) Atualiza estados dos elementos baseados no tempo que correu desde a última atualização     */
		/*    e no timestamp atual: posição e orientação, execução de disparos de projéteis, etc.        */
		/*                                                                                               */
		/* 3) Processa entrada do usuário (teclado) e atualiza estados do player conforme a necessidade. */
		/*                                                                                               */
		/* 4) Desenha a cena, a partir dos estados dos elementos.                                        */
		/*                                                                                               */
		/* 5) Espera um período de tempo (de modo que delta seja aproximadamente sempre constante).      */
		/*                                                                                               */
		/*************************************************************************************************/
		
		while(running){
		
			/* Usada para atualizar o estado dos elementos do jogo    */
			/* (player, projéteis e inimigos) "delta" indica quantos  */
			/* ms se passaram desde a última atualização.             */
			
			delta = System.currentTimeMillis() - currentTime;
			
			/* Já a variável "currentTime" nos dá o timestamp atual.  */
			
			currentTime = System.currentTimeMillis();
			
			/***************************/
			/* Verificação de colisões */
			/***************************/
						
			player.verificaColisaoPlayer(pprojectile, eprojectile, ship, worm);
				
			/***************************/
			/* Atualizações de estados */
			/***************************/
			
			/* projeteis (player) */
			pprojectile.atualiza();
			
			/* projeteis (inimigos) */
			eprojectile.atualiza();
			
			/* inimigos tipo 1 */
			ship.atualiza(eprojectile, player);
			
			/* inimigos tipo 2 */
			worm.atualiza(eprojectile);
			
			/* verificando se novos inimigos (tipo 1) devem ser "lançados" */
			ship.verifica();
			
			/* verificando se novos inimigos (tipo 2) devem ser "lançados" */
			worm.verifica();
			
			/* Verificando se a explosão do player já acabou.         */
			/* Ao final da explosão, o player volta a ser controlável */
			player.verificaExplosionPlayer();
			
			/********************************************/
			/* Verificando entrada do usuário (teclado) */
			/********************************************/
			player.verificaEntradaPlayer(pprojectile);
			
			/*******************/
			/* Desenho da cena */
			/*******************/
			
			/* desenhando plano fundo*/
			background.desenhaBackground();
						
			/* desenhando player */
			player.desenha();
				
			/* desenhando projeteis (player) */
			pprojectile.desenha();
			
			/* desenhando projeteis (inimigos) */
			eprojectile.desenha();
			
			/* desenhando inimigos (tipo 1) */
			ship.desenha();
			
			/* desenhando inimigos (tipo 2) */
			worm.desenha();
			
			/* chama a display() da classe GameLib atualiza o desenho exibido pela interface do jogo. */
			
			GameLib.display();
			
			/* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 5 ms. */
			
			busyWait(currentTime + 5);
		}
		
		System.exit(0);
	}
	
}

