package refatorado;

abstract class Enemy implements Observer{
	/* vari�veis dos inimigos tipo 1 */
	
	//int [] states;			// estados
	Cordinate  position;
	double V;					// velocidades
	double RV;					// velocidades de rota��o
	double explosion_start;		// instantes dos in�cios das explos�es
	double explosion_end;		// instantes dos finais da explos�es
	long nextShoot;				// instantes do pr�ximo tiro
	double radius;				// raio (tamanho do inimigo 1)
	long next;					// instante em que um novo inimigo 1 deve aparecer
	boolean exploding;
	
	Enemy(){
		//states = new int[size];
		//for(int i = 0; i < states.length; i++) states[i] = Main.INACTIVE;
		position = new Cordinate();
		exploding = false;
	}
	
	public void atualiza(){
	
	}
	
}
