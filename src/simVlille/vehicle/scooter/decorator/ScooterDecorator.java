package simVlille.vehicle.scooter.decorator;

import simVlille.vehicle.scooter.Scooter;

public abstract class ScooterDecorator extends Scooter {

    private final Scooter decoratedScooter;

    public ScooterDecorator(Scooter s){
        this.decoratedScooter = s;
    }

    @Override
    public int getWeight() {
        return this.decoratedScooter.getWeight();
    }

    @Override
    public String getDescription() {
        return this.decoratedScooter.getDescription()+" with";
    }
}
