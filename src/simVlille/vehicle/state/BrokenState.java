package simVlille.vehicle.state;

import simVlille.vehicle.Vehicle;

public class BrokenState implements VehicleState {
    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public boolean isBroken() {
        return true;
    }

    @Override
    public boolean isRented() {
        return false;
    }

    @Override
    public boolean isStolen() {
        return false;
    }
}

