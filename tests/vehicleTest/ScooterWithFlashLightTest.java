package vehicleTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simVlille.vehicle.scooter.Scooter;
import simVlille.vehicle.scooter.decorator.ScooterWithFlashLight;

import static org.junit.jupiter.api.Assertions.*;

public class ScooterWithFlashLightTest {

    private Scooter s;
    private ScooterWithFlashLight scooterWithFlashLight;

    @BeforeEach
    public void setUp(){
        this.s = new Scooter();
        this.scooterWithFlashLight = new ScooterWithFlashLight(s);
    }

    @Test
    public void getWeightTest(){
        int weightExpected = s.getWeight() + scooterWithFlashLight.FLASH_LIGHT_WEIGHT;
        assertEquals(weightExpected, scooterWithFlashLight.getWeight());
    }

    @Test
    public void getDescriptionTest(){
        String expectedDescription = scooterWithFlashLight.getDescription();
        int weightExpected = scooterWithFlashLight.getWeight();

        String actualDescription = "Scooter  with Flash Light";

        assertEquals(expectedDescription, actualDescription);
    }
}
