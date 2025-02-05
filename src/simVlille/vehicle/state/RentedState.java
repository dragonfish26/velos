package simVlille.vehicle.state;

import simVlille.vehicle.Vehicle;

public class RentedState implements VehicleState {
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
        return true;
    }

    @Override
    public boolean isStolen() {
        return false;
    }
}

