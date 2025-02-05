package simVlille.vehicle.bike.decorator;


import simVlille.vehicle.bike.Bike;

public class BikeWithBasket extends BikeDecorator{

    public static final  int BASKET_WEIGHT = 2;

    public BikeWithBasket(Bike b) {
        super(b);
    }

    @Override
    public int getWeight() {
        return super.getWeight()+BASKET_WEIGHT;
    }

    @Override
    public String getDescription() {
        return super.getDescription()+" Basket";
    }
}
