package refatorado.game;
import java.awt.Color;
import java.util.*;

import refatorado.gamelib.GameLib;
//COMMIT EXDRUXULO
class Background {
	
	/* estrelas que formam o fundo de primeiro plano */
	private List <Cordinate> background1;
	private double background1_speed;
	private double background1_count;
	
	/* estrelas que formam o fundo de segundo plano */
	private List <Cordinate> background2;
	private double background2_speed;
	private double background2_count;
	
	Background(){
		this.background1 = new ArrayList <Cordinate>();
		this.background2 = new ArrayList <Cordinate>();
		
		background1_speed = 0.070;
		background1_count = 0.0;
		background2_speed = 0.045;
		background2_count = 0.0;

		for (int i = 0; i < 20; i++){
			Cordinate novo = new Cordinate(Math.random() * GameLib.WIDTH, Math.random() * GameLib.HEIGHT);
			background1.add(novo);
		}
		
		for (int i = 0; i < 50; i++){
			Cordinate novo = new Cordinate(Math.random() * GameLib.WIDTH, Math.random() * GameLib.HEIGHT);
			background2.add(novo);
		}
	}
	
	void desenha(){
		/* desenhando plano fundo distante */
		GameLib.setColor(Color.DARK_GRAY);
		background2_count += background2_speed * Level.getDelta();
		
		for(Cordinate aux : background2){
			GameLib.fillRect(aux.x, (aux.y + background2_count) % GameLib.HEIGHT, 2, 2);
		}
		
		/* desenhando plano de fundo próximo */
		GameLib.setColor(Color.GRAY);
		background1_count += background1_speed * Level.getDelta();
		
		for(Cordinate aux : background1){
			GameLib.fillRect(aux.x, (aux.y + background1_count) % GameLib.HEIGHT, 3, 3);
		}
	}
}
