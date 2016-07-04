package refatorado;
import java.util.*;

abstract class Subject {
	List <Observer> observers;
	
	
	public Subject (){
		this.observers = new ArrayList <Observer>();
	}
	
	void addObserver(Observer observer){
		this.observers.add(observer);
	}
	
	void removeObserver(Observer observer){
		this.observers.remove(observer);
	}

	void notifyObservers(){
		for (Observer obs : observers){
			obs.atualiza();
		}
	}
	
}
