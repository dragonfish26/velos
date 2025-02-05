package simVlille.redistribution;

import simVlille.time.ControlCenter;
import simVlille.station.RentStation;
import simVlille.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomRedistribution implements Redistribution{
    // a list to temporarily store the available vehicles
    protected List <Vehicle> vehiclesToRe = new ArrayList<>();

    /**
     * This method executes the random redistribution on the available vehicles of a control center
     * @param cc The control center to redistribute the vehicles
     * @param <T> The type of the control center
     */
    public <T extends Vehicle> void operate(ControlCenter<T> cc) {
        System.out.println("** Before random redistribution:");
        cc.display();

        this.emptyTheStations(cc);
        int nbVehicles = vehiclesToRe.size();
        List<RentStation<T>> theStations = cc.getStations();
        int nbOfStations = theStations.size();

        for (Vehicle vehicle : vehiclesToRe) {
            RentStation station = getRandomStation(theStations);
            while (!station.stillHavePlace()) {
                station = getRandomStation(theStations);
            }
            station.addVehicle(vehicle);
        }

        vehiclesToRe.clear();

        System.out.println("** After redistribution:");
        cc.display();

    }

    /**
     * A method to choose a random station from a list of stations
     * @param theStations The list of stations to choose from
     * @return A random station
     * @param <T> The type of the RentStation
     */
    public <T extends Vehicle> RentStation<T> getRandomStation(List<RentStation<T>> theStations) {
        Random random = new Random();
        int randomIndex = random.nextInt(theStations.size());
        return theStations.get(randomIndex);
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
