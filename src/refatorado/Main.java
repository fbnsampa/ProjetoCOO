package refatorado;

public class Main{
	
	/* Constantes relacionadas aos estados que os elementos   */
	/* do jogo (player, projeteis ou inimigos) podem assumir. */
	
	public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;
	public static final int EXPLODING = 2;
	
	static Player player;
	
	/* Indica que o jogo est� em execu��o */
	static boolean running;

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
		running = true;

		player = new Player();
		Level level = new Level();
		Background background = new Background();

		
		
		
		
//		for (Pprojectile aux : pprojectiles){
//			if (aux.isOutOfScreen()) pprojectiles.remove(aux);
//			else aux.atualiza();
//		}
						
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
		
			level.run();
			
			/* desenhando plano fundo*/
			background.desenha();
			
			GameLib.display();
			
			/* faz uma pausa de modo que cada execu��o do la�o do main loop demore aproximadamente 5 ms. */
			
			busyWait(Level.currentTime + 5);
		}
		
		System.exit(0);
	}
	
}

