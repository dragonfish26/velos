package simVlille.time;

import simVlille.station.RentStation;
import simVlille.vehicle.Vehicle;

public interface Observer {
	
	/**
     * The update method of the observer
     *
     * @param station The station to update
     * @param v
     */
	void update(RentStation station, Vehicle v);
}
