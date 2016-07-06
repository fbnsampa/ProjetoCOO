package refatorado;
import java.util.*;

abstract class Enemy implements Observer{
	List <Eprojectile> projectiles;//para mexer quando for usar pacotes 
	protected Cordinate position;
	protected double V;						// velocidades
	protected double RV;					// velocidades de rotação
	protected double radius;				// raio (tamanho do inimigo 1)
	protected double explosion_start;		// instantes dos inícios das explosões
	protected double explosion_end;			// instantes dos finais da explosões
	protected boolean exploding;
	protected ShotBehavior sb;
	protected MoveBehavior mb;
	
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
		explosion_start = Level.getCurrentTime();
		explosion_end = Level.getCurrentTime() + 500;
	}

	public double getExplosion_end() {
		return explosion_end;
	}

	public void shoot (){
		this.sb.shoot(this);
	}
	
	public void move (){
		this.mb.move(this);
	}
	
	public void atualiza(){
	
	}
	
	public void desenha(){

	}
	
	public boolean isOutOfScreen(){
		return true;
	}
	
}
