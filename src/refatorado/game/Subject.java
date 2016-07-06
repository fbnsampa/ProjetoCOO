package refatorado.game;
import java.util.*;

abstract class Subject <T extends Observer> {
	List <T> observers;//para mexer depois
	
	public Subject (){
		observers = new ArrayList<T>(); 
	}
	
	void addObserver(T observer){
		this.observers.add(observer);
	}
	
	void removeObserver(T observer){
		this.observers.remove(observer);
	}

	void notifyObservers(){
		for (T observer : observers){
			observer.atualiza();
		}
	}
	
}
