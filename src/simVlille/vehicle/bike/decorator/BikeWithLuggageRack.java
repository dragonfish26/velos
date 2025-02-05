package simVlille.vehicle.bike.decorator;


import simVlille.vehicle.bike.Bike;

public class BikeWithLuggageRack extends BikeDecorator {

    public static final int LUGGAGE_RACK_WEIGHT = 5;

    public BikeWithLuggageRack(Bike b) {
        super(b);
    }

    @Override
    public int getWeight() {
        return super.getWeight() + LUGGAGE_RACK_WEIGHT;
    }

    @Override
    public String getDescription() {
        return super.getDescription()+" Luggage Rack";
    }
}
