package refatorado.game;

public class Cordinate {
	public double x;
	public double y;
	public double angle;
	
	public Cordinate (){
		this.x = 0.0;
		this.y = 0.0;
		this.angle = 0.0;
	}
	
	public Cordinate (double x, double y){
		this.x = x;
		this.y = y;
		this.angle = 0.0;
	}
	
	public Cordinate (double x, double y, double angle){
		this.x = x;
		this.y = y;
		this.angle = angle;
	}
	
	public double getDirectionX(){
		if (this.x > 0) return 1.0;
		if (this.x < 0) return -1.0;
		return 0.0;
	}
	
	public double getDirectionY(){
		if (this.y > 0) return 1.0;
		if (this.y < 0) return -1.0;
		return 0.0;
	}
}
