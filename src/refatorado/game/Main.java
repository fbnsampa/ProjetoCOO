package refatorado.game;

import java.awt.Font;
import java.io.*;
import java.util.Scanner;

import refatorado.gamelib.GameLib;

public class Main{
	
	public static Player player;
	public static boolean EndLevel;
	public static long EndLevelTime;
	private static boolean running; /* Indica que o jogo est� em execu��o */
	
	protected static void setRunning(boolean running) {
		Main.running = running;
	}

	/* Espera, sem fazer nada, at� que o instante de tempo atual seja */
	/* maior ou igual ao instante especificado no par�metro "time.    */
	private static void busyWait(long time){
		while(System.currentTimeMillis() < time) Thread.yield();
	}
	
	/* M�todo principal */
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
		
		/* Indica que o jogo est� em execu��o */
		running = true;
		Background background = new Background();

		/* iniciado interface gr�fica */
		
		GameLib.initGraphics();
		player = new Player(maxHP);
		
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
		
		for (int i = 0; i < numLevel; i++){
			Level level = new Level();
			level.load(fileNames[i]);
			EndLevel = false;
			while(running && !EndLevel){
				background.desenha();
				level.run();
					
				/* desenhando numero da fase*/
				Font font = new Font("Arial", Font.BOLD,13);
				GameLib.writeName(font, "FASE " + Integer.toString(i+1), 220, 55);
				
				/* desenhando plano fundo*/
				GameLib.display();
				
				/* faz uma pausa de modo que cada execu��o do la�o do main loop demore aproximadamente 5 ms. */
				busyWait(Level.getCurrentTime() + 5);
			}
			player.life.restoreHp();
			if (!running) break;
		}
		System.exit(0);
	}
	
}

