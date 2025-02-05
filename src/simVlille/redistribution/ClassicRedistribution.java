package simVlille.redistribution;

import simVlille.time.ControlCenter;
import simVlille.station.RentStation;
import simVlille.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class ClassicRedistribution implements Redistribution {
    // a list to temporarily store the available vehicles
    protected List<Vehicle> vehiclesToRe = new ArrayList<>();

    /**
     * This method executes the classic redistribution on the available vehicles of a control center
     * @param cc The control center to redistribute the vehicles
     * @param <T> The type of the control center
     */
    public <T extends Vehicle> void operate(ControlCenter<T> cc) {
        System.out.println("Before redistribution:");
        cc.display();

        this.emptyTheStations(cc);
        int i = 0;
        int nbVehicles = vehiclesToRe.size();
        List<RentStation<T>> theStations = cc.getStations();
        int nbOfStations = theStations.size();

        for (int j = 0; j < nbVehicles; j++) {
            RentStation<T> station = theStations.get(i % nbOfStations);
            if (!station.stillHavePlace()) {
                j--;
            } else {
                station.addVehicle((T) vehiclesToRe.get(j));
            }
            i++;
        }

        vehiclesToRe.clear();

        System.out.println("After redistribution:");
        cc.display();

    }

    /**
     * A method to temporarily empty all vehicles of the stations that is controlled by a given
     * control center and put them in a list
     * that is an attribute of the class
     * @param cc the control center to apply the method
     */
    public <T extends Vehicle> void emptyTheStations(ControlCenter<T> cc) {

        for (RentStation<?> rs : cc.getStations()) {
            vehiclesToRe.addAll(rs.getAvailableVehicles());
        }

        for (RentStation<T> rs : cc.getStations()){
            ArrayList<T> vehiclesInStation = rs.getAllVehicles();
            for (Vehicle v : vehiclesToRe) {
                if (vehiclesInStation.contains(v)) {
                    rs.removeVehicle(v);
                }
            }
        }
    }

}
