package simVlille.humans;

import simVlille.time.ControlCenter;
import simVlille.vehicle.Vehicle;
import simVlille.vehicle.state.StolenState;

import java.util.Random;

public class Thief implements Visitor{

    private ControlCenter<?> controlCenter;

    public Thief(ControlCenter<?> controlCenter){
        this.controlCenter = controlCenter;
    }

    @Override
    public void visit(Vehicle element) {

        Random random = new Random();

        // Generate a random number between 1 and 100 (inclusive)
        int randomNumber = random.nextInt(100) + 1;
        if (randomNumber >= 70){
            controlCenter.addToStolenVehicles(element);
            element.setState(new StolenState());
            controlCenter.removeFromAllVehicles(element);
            System.out.println("Thief stole "+ element.getDescription()+element.getId());
        }
    }
}
