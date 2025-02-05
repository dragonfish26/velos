package vehicleTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simVlille.vehicle.bike.Bike;
import simVlille.vehicle.bike.decorator.BikeWithLuggageRack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BikeWithLuggageRackTest {
    private Bike b;
    private BikeWithLuggageRack bikeWithLuggageRack;

    @BeforeEach
    public void setUp(){
        this.b = new Bike();
        this.bikeWithLuggageRack = new BikeWithLuggageRack(b);
    }

    @Test
    public void getWeightTest(){
        int weightExpected = b.getWeight()+BikeWithLuggageRack.LUGGAGE_RACK_WEIGHT;
        assertEquals(weightExpected, bikeWithLuggageRack.getWeight());
    }

    @Test
    public void getDescriptionTest(){
        String expectedDescription = bikeWithLuggageRack.getDescription();
        int weightExpected = b.getWeight()+BikeWithLuggageRack.LUGGAGE_RACK_WEIGHT;

        String actualDescription = "Bike with Luggage Rack";

        assertEquals(expectedDescription, actualDescription);
    }
}
