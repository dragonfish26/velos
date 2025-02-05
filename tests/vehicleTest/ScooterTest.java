package vehicleTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simVlille.vehicle.scooter.Scooter;

import static org.junit.jupiter.api.Assertions.*;

public class ScooterTest {

    private Scooter scooter;

    @BeforeEach
    public void setUp(){
        this.scooter = new Scooter();
    }

    @Test
    public void getWeightTest(){
        assertEquals(Scooter.SCOOTER_WEIGHT, scooter.getWeight());
    }

    @Test
    public void getDurabilityTest(){
        assertEquals(Scooter.DURABILTY, scooter.getDurability());
    }

    @Test
    public void getDescriptionTest(){
        String expectedDescription = scooter.getDescription();

        String actualDescription = "Scooter ";

        assertEquals(expectedDescription, actualDescription);
    }
}
