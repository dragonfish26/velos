package simVlille.vehicle.state;

import simVlille.vehicle.Vehicle;

public class StolenState implements VehicleState {
    @Override
    public boolean isAvailable() {
        return false;
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
        return true;
    }
}
