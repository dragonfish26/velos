package simVlille.station;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

import simVlille.time.Observer;
import simVlille.vehicle.*;
import simVlille.vehicle.state.RentedState;

public class RentStation<T extends Vehicle> implements Observable {

    private static int nextId = 1;
    private static final int VULNERABILITY = 2;

    /**Id of every Station*/
    protected int id;
    /**Capacity for every Station*/
    protected int capacity;
    /**List of every Vehicles in the Station*/
    private final ArrayList<T> theVehicles;
    /**List of every Obersvers for the Station*/
    private final List<Observer> theObservers;
    /**a boolean to indicate if the station needs redistribution or not*/
    private boolean isOk;
    /**an integer indicating for how many intervals the station hasn't been ok*/
    private int nbNOK;
    /** Allow to count the number of interval when the Station contains the same and only one Vehicle*/
    private int hasOneVehicle;
    // attribute that indicates the only one vehicle in the station if only one is in
    private T oneVehicle;

    /**
     * Constructor for every RentStation
     */
    public RentStation() {
        this.id = nextId++;
        this.capacity = generateRandomCapacity();
        this.theVehicles = new ArrayList<>();
        this.theObservers = new ArrayList<>();
        this.isOk = true;
        this.nbNOK = 0;
        this.hasOneVehicle = 0;
        this.oneVehicle = null;
    }

    /**
     * Allow to get a Random int [10:20] for the Station's capacity
     * @return an int [10:20]
     */
    private int generateRandomCapacity() {
        Random r = new Random();
        return r.nextInt(10, 21);
    }

    /**
     * Allow to get the Station capacity
     * @return the Station capaity
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Allow to get RentStation Id
     * @return the RentStation Id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Allow to get every Observers for the Station
     * @return every Observer in the Station
     */
    public List<Observer> getObservers(){
        return this.theObservers;
    }

    /**
     * allow to get every Vehicles in the Station
     * @return every vehicles in the Station
     */
    public ArrayList<T> getAllVehicles() {
        return this.theVehicles;
    }

    /**
     * allow to get Vehicles wich are Available
     * @return the Available Vehicles
     */
    public ArrayList<T> getAvailableVehicles() {
        ArrayList<T> lst = new ArrayList<>();

        for (T vehicle : theVehicles) {
            if (vehicle.isAvailable()) {
                lst.add(vehicle);
            }
        }
        return lst;
    }

    /**
     * allow to get the Broken Vehicles
     * @return the Broken Vehicles
     */
    public ArrayList<T> getBrokenVehicles() {
        ArrayList<T> lst = new ArrayList<>();

        for (T vehicle : theVehicles) {
            if (vehicle.isBroken()) {
                lst.add(vehicle);
            }
        }
        return lst;
    }

    /**
     * allow to get the Stolen Vehicles
     * @return the Stolen Vehicles
     */
    public ArrayList<T> getStolenVehicle() {
        ArrayList<T> lst = new ArrayList<>();

        for (T vehicle : theVehicles) {
            if (vehicle.isStolen()) {
                lst.add(vehicle);
            }
        }
        return lst;
    }

    /**
     * Allow to know if the RentStation still have place
     * @return True if the RentStation still have place / false otherwise
     */
    public boolean stillHavePlace() {
        return this.theVehicles.size() < this.capacity;
    }

    /**
     * Allow to know if the RentStation is empty (Contains 0 Vehicle)!
     * @return true if the RentStation is empty / false otherwise
     */
    public boolean isEmpty() {
        return this.theVehicles.isEmpty();
    }

    /**
     * Allow to add a Vehicle to the RentStation
     * @param v the Vehicle which is added
     */
    public void addVehicle(T v) {
        if (stillHavePlace()) {
            this.theVehicles.add(v);
        } else {
            System.out.println("The Station is full");
        }
    }

    /**
     * Allow to remove a Vehicle in the Station
     * @param v the Vehicle which is removed in the RentStation
     */
    public void removeVehicle(Vehicle v) {
        if (this.theVehicles.contains(v)) {
            this.theVehicles.remove(v);
        }
        /*
        else {
            System.out.println("The vehicle is not in the station");
        }*/
    }

    public void addObserver(Observer o) {
        this.theObservers.add(o);
    }

    public void removeObserver(Observer o) {
        this.theObservers.remove(o);
    }

    public void notifyObserver(Vehicle v) {
        for (Observer o : this.theObservers) {
            o.update(this, v);
        }
    }
    public void returnVehicle(T v) {
        if (stillHavePlace()) {
            this.theVehicles.add(v);
            //v.setState(VehicleState.AVAILABLE);
            v.incrementUsageCount();
        } else {
            System.out.println("The Station is full");
        }
    }

    /**
     * Check every condition for rent a Vehicle
     * make the v.state at Rented and remove the Vehicle from the Station if every conditions are checked
     * @param v the Vehicle we want to rent
     * @return true if the Vehicle is rented / false otherwise
     */
    public boolean rentVehicle(T v){
        if(v.isAvailable()){
            v.setState(new RentedState());
            this.theVehicles.remove(v);
            return true;
        } else {
            System.out.println("This Vehicle isn't available");
            return false;
        }
    }

    /**
     * allow to rent a random Vehicle in the Station
     * @return the Vehicle which is rented
     */
    public T rentRandomVehicle() {
        Random random = new Random();
        ArrayList<T> available = this.getAvailableVehicles();
        int i = random.nextInt(available.size());
        T selectedVehicle = available.get(i);

        if (rentVehicle(selectedVehicle)) {
            return selectedVehicle;
        }
        return null;
    }

    /**
     * allow to rent the first Available Vehicle in the Station
     * @return the Vehicle which is rented
     */
    public T rentFirstAvailableVehicle(){
        for(T vehicle: theVehicles){
            if(rentVehicle(vehicle)){
                return vehicle;
            }
        }
        System.out.println("No available Vehicle in the station");
        return  null;
    }

    /**
     * allow to rent a chosen Vehicle in the Station
     * @param v the Vehicle we want to rent
     */
    public void rentChosenVehicle(T v) {
        if (this.theVehicles.contains(v)) {
            rentVehicle(v);
        } else {
            System.out.println("This Vehicle is not in the Station.");
        }
    }

    /**
     * A method to update the attributes that indicates if a redistribution needs to happen
     */
    public void updateOk(){
        if (!stillHavePlace() || isEmpty()){
            nbNOK++;
        }
        if (nbNOK > 2){
            isOk = false;
        }
        if (stillHavePlace() && !isEmpty()){
            nbNOK=0;
            isOk = true;
        }
    }

    /**
     * Returns true if the station needs to be redistributed
     * @return The boolean value returned
     */
    public boolean needsToRedistribute(){
        return !isOk;
    }

    /**
     * Displays a description of the station.
     */
    public void getDescription() {
        String res = "The station's id is: " + getId() + " and its capacity is " + getCapacity();

        if (theVehicles.isEmpty()) {
            res = res + "\nNo vehicles in the station.";
        } else {
            res = res + "\nVehicles in the station:";
            for (T vehicle : theVehicles) {
                res = res + "\n" + vehicle.getDescription();
            }
        }
        System.out.println(res);
    }

    /**
     * Allow to know if the Station has only one Vehicle
     * @return true if the Station contains only One Vehicle
     */
    public boolean containsOneVehicle(){
        return this.theVehicles.size() == 1;
    }

    /**
     * Allow to increment by One hasOnlyVehicle
     */
    public void incrementHasOneVehicle(){
        if(this.containsOneVehicle()){
            this.hasOneVehicle++;
        }
    }

    /**
     * Allow to reset has One Vehicle when the Station has at least 2 Vehicle or the Vehicle change
     */
    public void resetHasOneVehicle(){
        this.hasOneVehicle = 0;
    }

    /**
     * Allow to know if a Station has only One Vehicle since 2 rounds at least
     */
    public void handleHasOneVehicle(){
        if (this.containsOneVehicle()){
            //this.incrementHasOneVehicle();
            //if the station has one vehicle and its the same as the one from the last interval, increment
            // hasOneVehicle
            if (this.oneVehicle == this.theVehicles.get(0)) {
                this.incrementHasOneVehicle();
            } else { //if the only one vehicle  isnt the same as last one, the has one vehicle is 1
                this.oneVehicle = this.theVehicles.get(0);
                this.hasOneVehicle = 1;
            }
        } else {
            this.resetHasOneVehicle();
        }
    }

    /**
     * allow to know if the Station is vulnerable!
     * @return true if the Station is vulnerable
     */
    public boolean isVulnerable(){
        return  this.hasOneVehicle >= VULNERABILITY;
    }
}