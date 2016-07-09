package refatorado.game.lifebar;
import refatorado.gamelib.GameLib;
import java.awt.Color;
import java.awt.Font;

public class LifeBarPlayer extends LifeBar {

	public LifeBarPlayer(int hp) {
		super(hp, "PLAYER");
		this.invulnerableDuration = 1000;
	}
	
	public void draw(){
		GameLib.setColor(Color.WHITE);
		GameLib.fillRect(100.0, 50, 152.0, 17.0);//JOGADOR
		GameLib.setColor(Color.RED);
		GameLib.fillRect(100.0, 50, 150.0, 15.0);//JOGADOR
		
		double aux = 150;
		
		if((hp) > 0){
			aux = (hp)*(150/maxhp);
			GameLib.setColor(new Color (0,180,0));
			GameLib.fillRect((100-(150-aux)/2), 50, aux, 15.0);
		}
		 
		Font playerfont = new Font("Arial", Font.BOLD,11);
		GameLib.writeName(playerfont, name, 83, 55);
		 
	}
	
	public void restoreHp(){
		this.hp = (int) maxhp;
		draw();
	}

}
