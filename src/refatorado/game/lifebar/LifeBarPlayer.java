package refatorado.game.lifebar;
import refatorado.gamelib.GameLib;
import java.awt.Color;
import java.awt.Font;

public class LifeBarPlayer extends LifeBar {

	public LifeBarPlayer(int hp) {
		super(hp);
		// TODO Auto-generated constructor stub
	}
	
	public void draw(){
		GameLib.setColor(Color.WHITE);
		GameLib.fillRect(100.0, 50, 152.0, 17.0);//JOGADOR
		GameLib.setColor(Color.RED);
		GameLib.fillRect(100.0, 50, 150.0, 15.0);//JOGADOR
		
		int aux = 150;
		
		if((hp) > 0){
			aux = (hp)*(150/maxhp);
			GameLib.setColor(Color.GREEN);
			GameLib.fillRect((100-(150-aux)/2), 50, aux, 15.0);
		}
		 
		Font playerfont = new Font("Arial", Font.BOLD,11);
		GameLib.writeName(playerfont, "PLAYER", 83, 55);
		 
	}
	
	public void restoreHp(){
		this.hp = maxhp;  
	}

}
