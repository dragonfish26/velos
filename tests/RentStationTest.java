import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import simVlille.station.RentStation;
import simVlille.vehicle.bike.Bike;
import simVlille.vehicle.bike.decorator.BikeWithBasket;
import simVlille.vehicle.state.*;
import simVlille.time.Observer;

class RentStationTest {
    private RentStation<Bike> station;
    private Bike availableBike;
    private Bike rentedBike;
    private Bike brokenBike;
    private Bike stolenBike;

    @BeforeEach
    void setUp() {
        station = new RentStation<>();
        availableBike = new Bike();
        rentedBike = new Bike();
        brokenBike = new Bike();
        stolenBike = new Bike();

        // Set the state of each bike
        availableBike.setState(new AvailableState());
        rentedBike.setState(new RentedState());
        brokenBike.setState(new BrokenState());
        stolenBike.setState(new StolenState());

        // Add bikes to the station
        station.addVehicle(availableBike);
        station.addVehicle(rentedBike);
        station.addVehicle(brokenBike);
        station.addVehicle(stolenBike);
    }

    @Test
    void addVehicleTest() {
        // Test adding a new vehicle
        Bike b = new Bike();
        station.addVehicle(b);
        assertTrue(station.getAllVehicles().contains(b));
    }

    @Test
    void removeVehicleTest() {
        // Test removing an existing vehicle
        station.removeVehicle(stolenBike);
        assertFalse(station.getAllVehicles().contains(stolenBike));
    }

    @Test
    void getAvailableVehiclesTest() {
        // Test retrieving available vehicles
        BikeWithBasket bw = new BikeWithBasket(new Bike());
        bw.setState(new AvailableState());
        station.addVehicle(bw);

        assertTrue(station.getAvailableVehicles().contains(availableBike));
        assertTrue(station.getAvailableVehicles().contains(bw));
    }

    @Test
    void getStolenVehiclesTest() {
        // Test retrieving stolen vehicles
        BikeWithBasket bw = new BikeWithBasket(new Bike());
        bw.setState(new StolenState());
        station.addVehicle(bw);

        assertTrue(station.getStolenVehicle().contains(stolenBike));
        assertTrue(station.getStolenVehicle().contains(bw));
    }

    @Test
    void getBrokenVehiclesTest() {
        // Test retrieving broken vehicles
        BikeWithBasket bw = new BikeWithBasket(new Bike());
        bw.setState(new BrokenState());
        station.addVehicle(bw);

        assertTrue(station.getBrokenVehicles().contains(brokenBike));
        assertTrue(station.getBrokenVehicles().contains(bw));
    }

    @Test
    void rentVehicleTest() {
        // Test renting a specific available vehicle
        assertEquals(4, station.getAllVehicles().size());
        assertTrue(station.rentVehicle(availableBike));
        assertInstanceOf(RentedState.class , availableBike.getState());
        assertFalse(station.getAllVehicles().contains(availableBike));
    }

    @Test
    void rentFirstAvailableVehicleTest() {
        // Test renting the first available vehicle
        assertEquals(4, station.getAllVehicles().size());
        assertEquals(availableBike, station.rentFirstAvailableVehicle(), "First available bike should be rented");
        assertFalse(station.getAllVehicles().contains(availableBike), "The rented bike should be removed from the station");
    }

    @Test
    void rentChosenVehicleTest() {
        // Test renting a specific chosen vehicle
        station.rentChosenVehicle(availableBike);
        assertTrue(availableBike.getState() instanceof RentedState, "The bike's state should change to RentedState");
        assertFalse(station.getAllVehicles().contains(availableBike), "The rented bike should be removed from the station");
    }

    @Test
    void rentNotAvailableVehicleTest() {
        // Test attempting to rent bikes in non-available states
        station.rentChosenVehicle(brokenBike);
        station.rentChosenVehicle(stolenBike);
        station.rentChosenVehicle(rentedBike);

        assertTrue(brokenBike.getState() instanceof BrokenState, "Broken bike's state should remain unchanged");
        assertTrue(stolenBike.getState() instanceof StolenState, "Stolen bike's state should remain unchanged");
        assertTrue(rentedBike.getState() instanceof RentedState, "Already rented bike's state should remain unchanged");
    }

    @Test
    void testStillHavePlace() {
        // Test station's capacity handling
        assertTrue(station.stillHavePlace(), "Station should have place for more vehicles");

        // Fill the station to its capacity
        for (int i = 0; i < station.getCapacity(); i++) {
            station.addVehicle(new Bike());
        }

        assertFalse(station.stillHavePlace(), "Station should not have place when at full capacity");
    }

    @Test
    void testIsEmpty() {
        // Test if the station is empty
        RentStation<Bike> emptyStation = new RentStation<>();
        assertTrue(emptyStation.isEmpty(), "Station should be empty initially");

        emptyStation.addVehicle(availableBike);
        assertFalse(emptyStation.isEmpty(), "Station should not be empty after adding a vehicle");
    }

    @Test
    void testNoAvailableVehicleToRent() {
        // Test renting when no vehicles are available
        station.getAvailableVehicles().forEach(station::removeVehicle);
        assertNull(station.rentFirstAvailableVehicle(), "Should return null when no available vehicles are present");
    }
}