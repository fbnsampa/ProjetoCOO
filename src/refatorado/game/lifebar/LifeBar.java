package refatorado.game.lifebar;

abstract class LifeBar {
	
	protected int hp;
	protected int maxhp;
	
	LifeBar(int hp){
		maxhp = hp;
		this.hp = hp;
		draw();
	}
	
	public boolean takeHit(){
		this.hp--;
		draw();
		if(this.hp == 0) return true;
		else return false;	
	}
	
	public void draw(){
		
	}

}
