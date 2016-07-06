package refatorado.game;
import java.util.*;

abstract class Subject <T extends Observer> {
	List <T> observers;//para mexer depois
	
	Subject (){
		observers = new ArrayList<T>(); 
	}
	
	protected void addObserver(T observer){
		this.observers.add(observer);
	}
	
	protected void removeObserver(T observer){
		this.observers.remove(observer);
	}

	protected void notifyObservers(){
		for (T observer : observers){
			observer.update();
		}
	}
	
}
