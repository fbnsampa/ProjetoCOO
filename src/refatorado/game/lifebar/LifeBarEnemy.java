package refatorado.game.lifebar;
import refatorado.gamelib.GameLib;
import java.awt.Color;
import java.awt.Font;

public class LifeBarEnemy extends LifeBar {
	
	public LifeBarEnemy(int hp, String name) {
		super(hp, name);
		this.invulnerableDuration = 100;
	}
	
	public void draw(){
		GameLib.setColor(Color.WHITE);
		GameLib.fillRect(380, 50, 152.0, 17.0);//ENEMY
		GameLib.setColor(Color.RED);
		GameLib.fillRect(380, 50, 150.0, 15.0);//ENEMY
		
		int aux = 150;
		
		if((hp) > 0){
			aux = (hp)*(150/maxhp);
			GameLib.setColor(Color.BLUE);
			GameLib.fillRect((380+(150-aux)/2), 50, aux, 15.0);
		}
		 
		Font enemyfont = new Font("Arial", Font.BOLD,11);
		GameLib.writeName(enemyfont, name, 353, 55);
	}

}
