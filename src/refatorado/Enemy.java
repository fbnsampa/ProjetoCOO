package refatorado;
import java.util.*;

abstract class Enemy implements Observer{
	/* vari�veis dos inimigos tipo 1 */
	List <Eprojectile> projectiles;
	
	int [] states;				// estados
	Cordinate position;
	double [] V;				// velocidades
	double [] RV;				// velocidades de rota��o
	long  explosion_start;	// instantes dos in�cios das explos�es
	long  explosion_end;	// instantes dos finais da explos�es
	long [] nextShoot;			// instantes do pr�ximo tiro
	double radius;				// raio (tamanho do inimigo 1)
	long next;					// instante em que um novo inimigo 1 deve aparecer
	boolean exploding;
	
	Enemy(int size){
		projectiles = new ArrayList<Eprojectile>();
		
		states = new int[size];
		for(int i = 0; i < states.length; i++) states[i] = Main.INACTIVE;
		exploding = false;
	}
	
	public void atualiza(){
	
	}
	
	public void verifica(){
		
	}
	
	public void desenha(){
		
	}
}
