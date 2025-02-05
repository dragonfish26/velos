package humansTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import simVlille.humans.Repairman;
import simVlille.vehicle.bike.Bike;
import simVlille.vehicle.state.BrokenState;
import simVlille.vehicle.state.AvailableState;
import simVlille.time.ControlCenter;

class RepairmanTest {
    private Repairman repairman;
    private ControlCenter<Bike> controlCenter;
    private Bike brokenBike;
    private Bike anotherBrokenBike;

    @BeforeEach
    void setUp() {
        controlCenter = new ControlCenter<>(Bike.class);
        repairman = new Repairman(controlCenter);

        // Création de vélos Broken pour tester leurs réparation
        brokenBike = new Bike();
        brokenBike.setState(new BrokenState());

        anotherBrokenBike = new Bike();
        anotherBrokenBike.setState(new BrokenState());
    }

    @Test
    void testVisit() {
        repairman.visit(brokenBike);
        assertTrue(repairman.isBusy());
        assertEquals(brokenBike, repairman.getRepairingVehicle());
    }

    @Test
    void testRepairVehicle() {
        repairman.visit(brokenBike);

        // Simule deux cycles de réparation

        // Premier Cycle
        repairman.repairVehicle();
        assertTrue(repairman.isBusy());
        assertEquals(new BrokenState().getClass(), brokenBike.getState().getClass());

        // Deuxième Cycle
        repairman.repairVehicle();
        assertFalse(repairman.isBusy());
        assertEquals(new AvailableState().getClass(), brokenBike.getState().getClass());
    }

    @Test
    void testRepairWithoutVisiting() {
        repairman.repairVehicle();
        assertFalse(repairman.isBusy());
        assertNull(repairman.getRepairingVehicle());
    }

    @Test
    void testMultipleRepairs() {
        repairman.visit(brokenBike);

        // Réparation du 1er vélo
        repairman.repairVehicle(); // Cycle 1
        repairman.repairVehicle(); // Cycle 2
        assertFalse(repairman.isBusy());

        // Réparation du 2e vélo
        repairman.visit(anotherBrokenBike);
        repairman.repairVehicle(); // Cycle 1
        repairman.repairVehicle(); // Cycle 2
        assertFalse(repairman.isBusy());
        assertEquals(new AvailableState().getClass(), anotherBrokenBike.getState().getClass());
    }
}