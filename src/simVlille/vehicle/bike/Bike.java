package simVlille.vehicle.bike;

import simVlille.vehicle.Vehicle;

/** a class for every type of Bike */
public class Bike extends Vehicle {

    public static final int BIKE_WEIGHT = 10;
    public static final int DURABILITY = 5;

    public Bike(){
        super();
        //this.maxRent = 2;
    }

    @Override
    public int getWeight() {
        return BIKE_WEIGHT;
    }

    @Override
    public int getDurability() {
        return DURABILITY;
    }

    @Override
    public String getDescription() {
        return "Bike";
    }

}