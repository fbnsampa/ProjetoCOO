package refatorado;

import java.awt.Color;

class Background {
	
	/* estrelas que formam o fundo de primeiro plano */
	
	double [] background1_X;
	double [] background1_Y;
	double background1_speed;
	double background1_count;
	
	/* estrelas que formam o fundo de segundo plano */
	
	double [] background2_X;
	double [] background2_Y;
	double background2_speed;
	double background2_count;
	
	Background(){
		background1_X = new double[20];
		background1_Y = new double[20];
		background1_speed = 0.070;
		background1_count = 0.0;

		background2_X = new double[50];
		background2_Y = new double[50];
		background2_speed = 0.045;
		background2_count = 0.0;

	}
	
	void inicializaBackground(){
		for(int i = 0; i < background1_X.length; i++){
			
			background1_X[i] = Math.random() * GameLib.WIDTH;
			background1_Y[i] = Math.random() * GameLib.HEIGHT;
		}
		
		for(int i = 0; i < background2_X.length; i++){
			
			background2_X[i] = Math.random() * GameLib.WIDTH;
			background2_Y[i] = Math.random() * GameLib.HEIGHT;
		}
	}
	void desenhaBackground(){
		/* desenhando plano fundo distante */
		
		GameLib.setColor(Color.DARK_GRAY);
		background2_count += background2_speed * Main.delta;
		
		for(int i = 0; i < background2_X.length; i++){
			
			GameLib.fillRect(background2_X[i], (background2_Y[i] + background2_count) % GameLib.HEIGHT, 2, 2);
		}
		
		/* desenhando plano de fundo próximo */
		
		GameLib.setColor(Color.GRAY);
		background1_count += background1_speed * Main.delta;
		
		for(int i = 0; i < background1_X.length; i++){
			
			GameLib.fillRect(background1_X[i], (background1_Y[i] + background1_count) % GameLib.HEIGHT, 3, 3);
		}
	}

}
