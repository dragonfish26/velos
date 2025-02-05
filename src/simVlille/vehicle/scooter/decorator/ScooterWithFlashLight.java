package simVlille.vehicle.scooter.decorator;

import simVlille.vehicle.scooter.Scooter;

public class ScooterWithFlashLight extends ScooterDecorator{

    public final int FLASH_LIGHT_WEIGHT = 1;

    public ScooterWithFlashLight(Scooter s) {
        super(s);
    }

    @Override
    public int getWeight() {
        return super.getWeight()+FLASH_LIGHT_WEIGHT;
    }

    @Override
    public String getDescription() {
        return super.getDescription()+" Flash Light";
    }
}
