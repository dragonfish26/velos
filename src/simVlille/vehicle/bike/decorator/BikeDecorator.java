package simVlille.vehicle.bike.decorator;

import simVlille.vehicle.bike.Bike;

public abstract  class BikeDecorator extends Bike {

    private final Bike decoratedBike;

    public BikeDecorator(Bike b){
        this.decoratedBike = b;
    }

    @Override
    public int getId(){
        return super.getId() - 1;
    }

    @Override
    public int getWeight() {
        return this.decoratedBike.getWeight();
    }

    @Override
    public String getDescription() {
        return this.decoratedBike.getDescription() + " with";
    }
}
