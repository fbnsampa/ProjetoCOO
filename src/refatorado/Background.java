package refatorado;
import java.awt.Color;
import java.util.*;

class Background {
	
	/* estrelas que formam o fundo de primeiro plano */
	List <Cordinate> background1;
	double background1_speed;
	double background1_count;
	
	/* estrelas que formam o fundo de segundo plano */
	List <Cordinate> background2;
	double background2_speed;
	double background2_count;
	
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
		background2_count += background2_speed * Main.delta;
		
		for(Cordinate aux : background2){
			GameLib.fillRect(aux.x, (aux.y + background2_count) % GameLib.HEIGHT, 2, 2);
		}
		
		
		/* desenhando plano de fundo próximo */
		
		GameLib.setColor(Color.GRAY);
		background1_count += background1_speed * Main.delta;
		
		for(Cordinate aux : background1){
			GameLib.fillRect(aux.x, (aux.y + background1_count) % GameLib.HEIGHT, 3, 3);
		}
		
	}

}
