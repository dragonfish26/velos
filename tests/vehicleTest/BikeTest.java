package vehicleTest;

import org.junit.jupiter.api.Test;
import simVlille.vehicle.Vehicle;
import simVlille.vehicle.bike.Bike;

import static org.junit.jupiter.api.Assertions.*;

public class BikeTest extends VehicleTest {

    @Override
    protected Vehicle createVehicle() {
        return new Bike();
    }

    @Test
    public void getWeightTest(){
        assertEquals(Bike.BIKE_WEIGHT, mockVehicle.getWeight());
    }

    @Test
    public void getDurabilityTest(){
        assertEquals(Bike.DURABILITY, mockVehicle.getDurability());
    }

    @Test
    public void getDescriptionTest(){

        String expectedDescription = mockVehicle.getDescription();

        String actualDescription = "Bike";

        assertEquals(expectedDescription, actualDescription);
    }
}
