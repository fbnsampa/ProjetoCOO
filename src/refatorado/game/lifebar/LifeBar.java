package refatorado.game.lifebar;

import refatorado.game.Level;

abstract class LifeBar {
	
	protected int hp;
	protected double maxhp;
	protected boolean vulnerable;
	protected long invulnerableEnd; 	
	protected long invulnerableDuration;
	protected String name;
	
	LifeBar(int hp, String name){
		maxhp = hp;
		this.hp = hp;
		this.name = name;
		vulnerable = true;
		invulnerableEnd = System.currentTimeMillis();
		draw();
	}
	
	//retorna verdadeiro caso o hp atingir zero
	public boolean takeHit(){
		
		if (!vulnerable && (Level.getCurrentTime() > invulnerableEnd)) vulnerable = true;
		
		if (vulnerable){
			this.hp--;
			vulnerable = false;
			invulnerableEnd = Level.getCurrentTime() + invulnerableDuration;
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
