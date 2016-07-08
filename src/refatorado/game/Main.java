package refatorado.game;

import java.io.*;
import java.util.Scanner;

import refatorado.gamelib.GameLib;


public class Main{
	
	/* Constantes relacionadas aos estados que os elementos   */
	/* do jogo (player, projeteis ou inimigos) podem assumir. */

	public static Player player;
	public static boolean EndLevel;
	public static long EndLevelTime;
	/* Indica que o jogo está em execução */
	private static boolean running;

	
	protected static void setRunning(boolean running) {
		Main.running = running;
	}

	/* Espera, sem fazer nada, até que o instante de tempo atual seja */
	/* maior ou igual ao instante especificado no parâmetro "time.    */
	//teste
	private static void busyWait(long time){
		
		while(System.currentTimeMillis() < time) Thread.yield();
	}
	
	/* Método principal */
	public static void main(String [] args){
		
		//leitura de arquivo
		int maxHP = 0;
		int numLevel = 0;
		String [] fileNames = new String [1];
		File file = new File (args[0]);
		
		try (Scanner in = new Scanner(file)){
			maxHP = in.nextInt();
			numLevel = in.nextInt();
			in.nextLine();
			fileNames = new String [numLevel];

			for (int i = 0; i < numLevel; i++)
				fileNames[i] = in.nextLine();
			
		} catch (FileNotFoundException x){
			System.out.println("'" + args[0] + "'" + " file not found!");
			x.printStackTrace();			
		}
		
		/* Indica que o jogo está em execução */
		running = true;
		Background background = new Background();

		/* iniciado interface gráfica */
		
		GameLib.initGraphics();
		player = new Player();
		
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
		
		for (int i = 0; i < numLevel; i++){
			Level level = new Level();
			level.load(fileNames[i]);
			EndLevel = false;
			System.out.println("Novo Level!");
			while(running && !EndLevel){
			
				level.run();
				
				/* desenhando plano fundo*/
				background.desenha();
				
				GameLib.display();
				
				/* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 5 ms. */
				
				busyWait(Level.getCurrentTime() + 5);
				
				//if (EndLevel && Level.getCurrentTime() > EndLevelTime) break;
			}
		}
		System.exit(0);
	}
	
}

