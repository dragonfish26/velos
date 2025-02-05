package simVlille.vehicle.state;

import simVlille.vehicle.Vehicle;

public interface VehicleState {
    boolean isAvailable();
    boolean isBroken();
    boolean isRented();
    boolean isStolen();
}
