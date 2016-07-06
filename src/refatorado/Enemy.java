package refatorado;
import java.util.*;

abstract class Enemy implements Observer{
	List <Eprojectile> projectiles; 
	Cordinate  position;
	double V;					// velocidades
	double RV;					// velocidades de rota��o
	double radius;				// raio (tamanho do inimigo 1)
	double explosion_start;		// instantes dos in�cios das explos�es
	double explosion_end;			// instantes dos finais da explos�es
	long nextShoot;				// instantes do pr�ximo tiro
	boolean exploding;
	
	Enemy(){
		projectiles = new ArrayList <Eprojectile>();
		position = new Cordinate();
		exploding = false;
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
