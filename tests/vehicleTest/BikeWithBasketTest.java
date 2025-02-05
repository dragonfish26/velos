package vehicleTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import simVlille.vehicle.bike.Bike;
import simVlille.vehicle.bike.decorator.BikeWithBasket;

public class BikeWithBasketTest {

    private Bike b;
    private BikeWithBasket bikeWithBasket;

    @BeforeEach
    public void setUp(){
        this.b = new Bike();
        this.bikeWithBasket = new BikeWithBasket(b);
    }

    @Test
    public void getWeightTest(){
        int weightExpected = b.getWeight()+BikeWithBasket.BASKET_WEIGHT;
        assertEquals(weightExpected, bikeWithBasket.getWeight());
    }

    @Test
    public void getDescriptionTest(){
        String expectedDescription = bikeWithBasket.getDescription();
        int weightExpected = bikeWithBasket.getWeight();

        String actualDescription = "Bike with Basket";

        assertEquals(expectedDescription, actualDescription);
    }
}
