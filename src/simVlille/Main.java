package simVlille;

import simVlille.time.ControlCenter;
import simVlille.time.TimeManager;
import simVlille.vehicle.bike.Bike;
import simVlille.vehicle.scooter.Scooter;

public class Main {

    public static void main(String[] args) {
        // Centre de contrôle pour les vélos
        ControlCenter<Bike> bikeControlCenter = new ControlCenter<>(Bike.class);

        // Centre de contrôle pour les scooters
        ControlCenter<Scooter> scooterControlCenter = new ControlCenter<>(Scooter.class);

        // Gestion du temps pour les vélos
        TimeManager bikeTimeManager = new TimeManager();
        bikeTimeManager.setControlCenter(bikeControlCenter);
        bikeTimeManager.setTotalInterval(15);

        // Gestion du temps pour les scooters
        TimeManager scooterTimeManager = new TimeManager();
        scooterTimeManager.setControlCenter(scooterControlCenter);
        scooterTimeManager.setTotalInterval(15);

        // Affichage initial
        System.out.println("---- Initial Bike Stations ----");
        bikeControlCenter.display();
        System.out.println("---- Initial Scooter Stations ----");
        scooterControlCenter.display();

        // Déroulement du temps
        bikeTimeManager.startFlow();
        //scooterTimeManager.startFlow();
    }
}
