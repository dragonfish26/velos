package simVlille.vehicle;


import simVlille.humans.Visitor;
import simVlille.vehicle.state.*;
import simVlille.vehicle.state.VehicleState;

/**An abstract class for every type of Vehicle */
public abstract class Vehicle{

    /**To start the First Bike has id = 1*/
    private static int nextId = 1;
    
    /** The Vehicle ID */
    protected int id;
    /** VehicleState allow to know the Vehicle's state*/
    protected VehicleState state;
    /**The Number of time the Vehicle has been used */
    protected int usageCount;
    /**The rental time for a Vehicle */
    protected int maxRent;
    /**The ID of the current user renting the vehicle (-1 if not rented). */
    protected int currentUserId;


    /**
     * Constructor for a Vehicle
     */
    public Vehicle(){
        this.state = new AvailableState();
        this.id = nextId++;
        this.usageCount = 0;
        this.maxRent = 2;
        this.currentUserId = -1;
    }

    /**
     * Gets a description of the vehicle.
     *
     * @return The description of the vehicle.
     */
    public abstract String getDescription();

    /**
     * allow to get the Bike's weight which is different for each type of Bike
     * @return the Bike's weight
     */
    public abstract int getWeight();

    /**
     * allow to get the Durability for every Bike
     * it's the number of time a Bike can be rented 
     * @return the durability for every Bike
     */
    public abstract int getDurability();

    /**
     * allow to get the Vehicle's State
     * @return AVAILABLE / BROKEN /  RENTAL / STOLEN   
     */
    public VehicleState getState(){
        return this.state;
    }

    /**
     * allow to get the Bike's id to identify each Bike
     * @return the Bike's id
     */
    public int getId(){
        return this.id;
    }

    /**
     * Allow to set the Vehicle's state after each actions on Vehicles
     * @param state the Vehicle's state
     */
    public void setState(VehicleState state){ this.state = state;}

    /**
     * allow to set the number of time the bike has been rented
     * @param usageCount number of time the Bike hasten rented after a new renting
     */
    public void setUsageCount(int usageCount){
        this.usageCount = usageCount;
    }

    /**
     * allow to increment the Usage count for this bike and update the Bike State
     * if it has been rented enough
     */
    public void incrementUsageCount(){

        this.usageCount++;
        if (this.usageCount >= this.maxRent){
            this.state = new BrokenState();
        } else {
            this.state = new AvailableState();
        }
    }

    /**
     * allow to know if the Vehicle is Available
     * @return True if the Vehicle is Available / False otherwise
     */
    public boolean isAvailable(){
        return this.state.isAvailable();
    }

    /**
     * allow to know if the Vehicle is Broken
     * @return True if the Vehicle is Broken / False otherwise
     */
    public boolean isBroken(){
        return this.state.isBroken();
    }

    /**
     * allow to know if the Vehicle is Rentable
     * @return True if the Vehicle is Rentable / False otherwise
     */
    public boolean isRented(){
        return this.state.isRented();
    }

    /**
     * allow to know if the Vehicle is Stolen
     * @return True if the Vehicle is Stolen / False otherwise
     */
    public boolean isStolen(){
        return this.state.isStolen();
    }

    public void accept(Visitor v){
        v.visit(this);
    }
}