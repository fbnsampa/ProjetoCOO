package refatorado.game.lifebar;

import refatorado.game.Level;

abstract class LifeBar {
	
	protected int hp;
	protected int maxhp;
	protected boolean vulnerable;
	protected long invulnerableEnd;
	
	LifeBar(int hp){
		maxhp = hp;
		this.hp = hp;
		vulnerable = true;
		invulnerableEnd = System.currentTimeMillis();
		draw();
	}
	
	//retorna verdadeiro caso o hp atingir zero
	public boolean takeHit(){
		
		if (!vulnerable && (Level.getCurrentTime() > invulnerableEnd)) vulnerable = true;
//		else System.out.println("CurrentTime = " + Level.getCurrentTime() + "   invulnerableEnd = " + invulnerableEnd);
		
		if (vulnerable){
			this.hp--;
			vulnerable = false;
			invulnerableEnd = Level.getCurrentTime() + 1000;
			draw();
			if(this.hp == 0) return true;
		}
		return false;	
	}
	
	public boolean isVulnerable(){
		return this.vulnerable;
	}
	
	public void draw(){

	}

}
