package refatorado;
import java.util.*;

abstract class Enemy implements Observer{
	List <Eprojectile> projectiles;//para mexer quando for usar pacotes 
	protected Cordinate  position;
	protected double V;					// velocidades
	protected double RV;					// velocidades de rotação
	protected double radius;				// raio (tamanho do inimigo 1)
	protected double explosion_start;		// instantes dos inícios das explosões
	protected double explosion_end;			// instantes dos finais da explosões
	protected boolean exploding;
	
	Enemy(){
		projectiles = new ArrayList <Eprojectile>();
		position = new Cordinate();
		exploding = false;
	}
	
	public double getPositionX() {
		return position.x;
	}
	
	public double getPositionY() {
		return position.y;
	}

	public double getRadius() {
		return radius;
	}

	public boolean isExploding() {
		return exploding;
	}

	public void setExploding() {
		exploding = true;
		explosion_start = Level.currentTime;
		explosion_end = Level.currentTime + 500;
	}

	public double getExplosion_end() {
		return explosion_end;
	}

	public void atualiza(){
	
	}
	
	public void desenha(){
		System.out.println("Teste");
	}
	
	public boolean isOutOfScreen(){
		return true;
	}
	
}
