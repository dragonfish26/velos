package simVlille.humans;

import simVlille.vehicle.Vehicle;

public interface Visitor {

    /**
     * Visits an element of Type T
     *
     * @param element The element to be visited
     */
    void visit(Vehicle element);
}
