package vehicleTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import simVlille.vehicle.Vehicle;
import simVlille.vehicle.state.*;

public abstract class VehicleTest {


    protected Vehicle mockVehicle;

    @BeforeEach
    public void setUp(){
        this.mockVehicle = createVehicle();
        //this.mockVehicle = new MockVehicle("mockVehicle", 10, 5);
    }


    protected abstract Vehicle createVehicle();

    @Test
    public void getDescriptionTest(){
        assertEquals("mockVehicle", mockVehicle.getDescription());
    }

    @Test
    public void getWeightTest(){
        assertEquals(10, mockVehicle.getWeight());
    }

    @Test
    public void getDurabilityTest(){
        assertEquals(5, mockVehicle.getDurability());
    }

    @Test
    public void getStateTest(){
        assertTrue(mockVehicle.getState() instanceof AvailableState);
    }

    @Test
    public void setStateTest() {
        assertTrue(mockVehicle.getState() instanceof AvailableState);

        mockVehicle.setState(new RentedState());
        assertTrue(mockVehicle.getState() instanceof RentedState);
    }

    @Test
    public void isAvailableTest(){
        assertTrue(mockVehicle.isAvailable());
    }

    @Test
    public void isBrokenTest(){
        assertFalse(mockVehicle.isBroken());
        mockVehicle.setState(new BrokenState());
        assertTrue(mockVehicle.isBroken());
    }

    @Test
    public void isStolenTest(){
        assertFalse(mockVehicle.isStolen());
        mockVehicle.setState(new StolenState());
        assertTrue(mockVehicle.isStolen());
    }

    @Test
    public void isRentedTest(){
        assertFalse(mockVehicle.isRented());
        mockVehicle.setState(new RentedState());
        assertTrue(mockVehicle.isRented());
    }
}