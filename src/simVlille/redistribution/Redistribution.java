package simVlille.redistribution;

import simVlille.time.ControlCenter;
import simVlille.vehicle.Vehicle;

//abstract class that handles the redistribution strategy for control centers
public interface Redistribution {

    /**
     * This method executes the redistribution method on the available vehicles of a control center
     * @param controlCenter The control center to redistribute the vehicles
     * @param <T> The type of the control center
     */
    public <T extends Vehicle> void operate(ControlCenter<T> controlCenter);

    /**
     * A method to temporarily empty all vehicles of the stations that is controlled by a given
     * control center and put them in a list
     * that is an attribute of the class
     * @param cc the control center to apply the method
     */
    public <T extends Vehicle> void emptyTheStations(ControlCenter<T> cc);


}
