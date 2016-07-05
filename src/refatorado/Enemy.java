package refatorado;
import java.util.*;

abstract class Enemy implements Observer{
	List <Eprojectile> projectiles; 
	Cordinate  position;
	double V;					// velocidades
	double RV;					// velocidades de rotação
	double radius;				// raio (tamanho do inimigo 1)
	long explosion_start;		// instantes dos inícios das explosões
	long explosion_end;			// instantes dos finais da explosões
	long nextShoot;				// instantes do próximo tiro
	boolean exploding;
	
	Enemy(){
		projectiles = new ArrayList <Eprojectile>();
		position = new Cordinate();
		exploding = false;
	}
	
	public void atualiza(){
	
	}
	
	public void desenha(){
		
	}
	
	public boolean isOutOfScreen(){
		return true;
	}
	
}
