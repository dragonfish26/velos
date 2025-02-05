package simVlille.station;

import simVlille.time.Observer;
import simVlille.vehicle.Vehicle;

public interface Observable {
	
	/**
	 * Method to notify observer
	 */
	void notifyObserver(Vehicle v);
	
	/**
	 * Add an observer
	 * @param o The observer to add
	 */
	void addObserver(Observer o);
	
	/**
	 * Remove an observer
	 * @param o The observer to remove 
	 */
	void removeObserver(Observer o);

}
