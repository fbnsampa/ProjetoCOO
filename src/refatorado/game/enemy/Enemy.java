package refatorado.game.enemy;
import java.util.*;
import refatorado.game.Cordinate;
import refatorado.game.Level;
import refatorado.game.Observer;
import refatorado.game.lifebar.LifeBarEnemy;
import refatorado.game.projectile.Eprojectile;
import refatorado.game.Character;

public abstract class Enemy implements Observer, Character, Comparable<Enemy>{
	public List <Eprojectile> projectiles;//para mexer quando for usar pacotes 
	public LifeBarEnemy life;
	protected Cordinate position;
	protected Cordinate speed;
	protected double RV;					// velocidades de rotação
	protected double radius;				// raio (tamanho do inimigo 1)
	protected double explosion_start;		// instantes dos inícios das explosões
	protected double explosion_end;			// instantes dos finais da explosões
	protected boolean exploding;
	protected boolean update;
	protected long spawn;
	protected ShotBehavior sb;
	protected MoveBehavior mb;
	
	Enemy(){
		life = new LifeBarEnemy(1);
		projectiles = new ArrayList <Eprojectile>();
		position = new Cordinate();
		speed = new Cordinate();
		exploding = false;
		update = false;
	}
	
	Enemy(double x, double y, long spawn){
		life = new LifeBarEnemy(1);
		projectiles = new ArrayList <Eprojectile>();
		position = new Cordinate(x, y);
		speed = new Cordinate();
		exploding = false;
		update = false;
		this.spawn = spawn;
	}
	
	public double getPositionX() {
		return position.x;
	}
	
	public double getPositionY() {
		return position.y;
	}
	
	public double getSpeedX() {
		return speed.x;
	}
	
	public double getSpeedY() {
		return speed.y;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public double getDirectionX(){
		return speed.getDirectionX();
	}
	
	public double getDirectionY(){
		return speed.getDirectionY();
	}
	
	public long getSpawn(){
		return this.spawn;
	}
	
	public boolean isExploding() {
		return exploding;
	}
	
	public boolean isVulnerable() {
		return this.life.isVulnerable();
	}
	
	public void setExploding() {
		this.exploding = true;
		this.explosion_start = Level.getCurrentTime();
		this.explosion_end = Level.getCurrentTime() + 500;
	}

	public double getExplosion_end() {
		return explosion_end;
	}

	public void setUpdateON(){
		this.update = true;
	}
	
	public int compareTo(Enemy e){
		if (this.spawn > e.spawn) return 1;
		if (this.spawn < e.spawn) return -1;
		return 0;
	}
	
	public void shoot (){
		this.sb.shoot(this);
	}
	
	public void move (){
		this.mb.move(this);
	}
	
	public boolean isOutOfScreen(){
		return false;
	}
	
	public void update(){
	
	}
	
	public void draw(){

	}
	
}
