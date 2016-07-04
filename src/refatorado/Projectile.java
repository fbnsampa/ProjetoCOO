package refatorado;

abstract class Projectile {
	
	int [] states;				// estados
	Cordinate [] position;
	Cordinate [] speedy;

	Projectile(int size){
		states = new int[size];
		for(int i = 0; i < states.length; i++) states[i] = Main.INACTIVE;
	}
	
	void atualiza(){
		/* projeteis (player) */
		
		for(int i = 0; i < states.length; i++){
			
			if(states[i] == Main.ACTIVE){
				/* verificando se proj�til saiu da tela */
				if(position[i].y < 0) {
					states[i] = Main.INACTIVE;
				}
				else {
					position[i].x += speedy[i].x * Main.delta;
					position[i].y += speedy[i].y * Main.delta;
				}
			}
		}
	}
	
	void desenha(){

	}
}
