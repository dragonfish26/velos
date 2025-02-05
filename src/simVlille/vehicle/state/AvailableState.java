package simVlille.vehicle.state;

import simVlille.vehicle.Vehicle;

public class AvailableState implements VehicleState {
    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public boolean isBroken() {
        return false;
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
