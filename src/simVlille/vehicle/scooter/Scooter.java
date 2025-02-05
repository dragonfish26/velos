package simVlille.vehicle.scooter;

import simVlille.vehicle.Vehicle;

public class Scooter extends Vehicle {

    public static final int SCOOTER_WEIGHT = 5;
    public static final int DURABILTY = 5;

    public Scooter(){
        super();
    }

    @Override
    public int getWeight() {
        return SCOOTER_WEIGHT;
    }

    @Override
    public int getDurability() {
        return DURABILTY;
    }

    @Override
    public String getDescription() {
        return "Scooter ";
    }
}
