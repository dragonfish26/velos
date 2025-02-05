package simVlille.time;
import java.util.*;

import simVlille.humans.Repairman;
import simVlille.redistribution.*;
import simVlille.station.*;
import simVlille.vehicle.Vehicle;
import simVlille.vehicle.bike.Bike;
import simVlille.vehicle.bike.decorator.BikeWithBasket;
import simVlille.vehicle.bike.decorator.BikeWithLuggageRack;
import simVlille.vehicle.scooter.Scooter;
import simVlille.vehicle.scooter.decorator.ScooterWithFlashLight;
import simVlille.vehicle.state.StolenState;


/**
 * A class for every Control Center
 */
public class ControlCenter<T extends Vehicle> implements Observer {

    private static int nextId = 1;
    
    private final int vehicleDurability;
    private int id;

    private List<RentStation<T>> theStations;
    //vehicles that are being rented
    private List<Vehicle> theRentalVehicles;
    private List<Vehicle> theStolenVehicle;
    private Redistribution redistributionMethod;

    private boolean isRedistributing;
    private Repairman repairman;

    

    /** The Constructor for every ControlCenter */
    public ControlCenter(Class<T> vehicleType){
        this.vehicleDurability = 3;
        this.id = nextId++;
        this.theStations = new ArrayList<>();
        this.theRentalVehicles = new ArrayList<>();
        this.theStolenVehicle = new ArrayList<>();
        this.isRedistributing = false;
        //start with classic redistribution
        this.redistributionMethod = new RandomRedistribution();
        this.repairman = new Repairman(this);
        initializeStations(vehicleType); // Initialisation automatique des stations
    }

    /**
     * Initialise les stations et leurs véhicules en fonction du type de véhicule.
     *
     * @param vehicleType Le type de véhicule à utiliser (Bike ou Scooter).
     */
    private void initializeStations(Class<T> vehicleType) {
        Random random = new Random();

        // Crée 6 stations
        for (int i = 0; i < 6; i++) {
            RentStation<T> station = new RentStation<>();
            this.theStations.add(station);

            // Ajoute 5 véhicules aléatoires par station
            for (int j = 0; j < 5; j++) {
                station.addVehicle(generateRandomVehicle(vehicleType, random));
            }

            // Ajoute des véhicules spécifiques à la première station
            if (i == 0) {
                if (vehicleType.equals(Bike.class)) {
                    station.addVehicle((T) new BikeWithBasket(new Bike()));
                    station.addVehicle((T) new BikeWithLuggageRack(new Bike()));
                } else if (vehicleType.equals(Scooter.class)) {
                    station.addVehicle((T) new ScooterWithFlashLight(new Scooter()));
                }
            }
        }
    }

    /**
     * Génère un véhicule aléatoire en fonction de son type.
     *
     * @param vehicleType Le type de véhicule (Bike ou Scooter ou ...).
     * @param random      Instance de Random utilisée pour la génération.
     * @return Une instance de T ou l'un de ses décorateurs.
     */
    private T generateRandomVehicle(Class<T> vehicleType, Random random) {
        if (vehicleType.equals(Bike.class)) {
            int bikeType = random.nextInt(3); // Valeur entre 0 et 2
            return switch (bikeType) {
                case 0 -> (T) new Bike();
                case 1 -> (T) new BikeWithBasket(new Bike());
                case 2 -> (T) new BikeWithLuggageRack(new Bike());
                default -> throw new IllegalStateException("Arrivera Jamais");
            };
        } else if (vehicleType.equals(Scooter.class)) {
            int scooterType = random.nextInt(2); // Valeur entre 0 et 1
            return switch (scooterType) {
                case 0 -> (T) new Scooter();
                case 1 -> (T) new ScooterWithFlashLight(new Scooter());
                default -> throw new IllegalStateException("Unexpected value: " + scooterType);
            };
        } else {
            throw new IllegalArgumentException("Arrivera jamais");
        }
    }

    /**
     * A method to get the repairman of a control station
     * @return a repairman
     */
    public Repairman getRepairman(){
        return this.repairman;
    }

    /**
     * Returns the list of rental vehicles managed by the control center.
     *
     * @return An ArrayList of Vehicle objects representing the rental vehicles.
     */
    public List<Vehicle> getTheRentalVehicles(){
        return theRentalVehicles;
    }

    /**
     * A method to add to the list of vehicles being rented
     * @param v The vehicle to add
     */
    public void addToRentalVehicles(Vehicle v){
        this.theRentalVehicles.add(v);
    }

    /**
     * Allow to add a Vehicle to the Stolen Vehicles
     * @param v the Vehicles wich is stolen
     */
    public void addToStolenVehicles(Vehicle v){
        this.theStolenVehicle.add(v);
    }

    /**
     * Removes a vehicle from the list of rented vehicles
     * @param v The vehicle to remove
     */
    public void removeFromRentalVehicles(Vehicle v){
        this.theRentalVehicles.remove(v);
    }


    /**
     * Allow to remove the Vehicle of his station and every Vehicles
     * @param v
     */
    public void removeFromAllVehicles(Vehicle v){
        for(RentStation station : theStations){
            station.removeVehicle(v);
        }
    }

    /**
     * Retrieves the list of broken vehicles.
     *
     * @return An ArrayList of broken vehicles.
     */
    public List<Vehicle> getBrokenVehicles(){
        List<Vehicle> brokenVehicles = new ArrayList<>();
        for (RentStation<T> rs : theStations){
            brokenVehicles.addAll(rs.getBrokenVehicles());
        }
        return brokenVehicles;
    }

    /**
     * A method to get all the available vehicles in the stations managed by the control station
     * @return the list of available vehicles
     */
    public List<Vehicle> getAvailableVehicles(){
        List<Vehicle> availableVehicles = new ArrayList<>();
        for (RentStation<T> rs : theStations){
            availableVehicles.addAll(rs.getAvailableVehicles());
        }
        return availableVehicles;
    }
    
    /**
     * @return tThe list of stations for this ControlCenter
     */
    public List<RentStation<T>> getStations(){
    	return this.theStations;
    }


    /**
     * Redistribute using the redistribution method
     */
    public void redistribute(){
        this.redistributionMethod.operate(this);
    }

	/**
	 * Updates the ControlCenter when the observable's state changes
	 */
	public void update(RentStation station, Vehicle v) {
		System.out.print(v.getDescription()+" "+v.getId()+ "- S" + station.getId() + "\n");
		
	}

    public void addToStations(RentStation<T> rs){
        this.theStations.add(rs);
    }

    /**
     * Affiche l'état du centre de contrôle et de ses stations.
     */
    public void display() {
        System.out.println("\nControl Center Status: ");
        for (RentStation<T> rs : theStations) {
            System.out.println("\nStation " + rs.getId() + ": ");
            System.out.println("- - - - - - - - - -");
            for (Vehicle v : rs.getAllVehicles()) {
                System.out.print(v.getDescription() + " " + v.getId() + "|");
            }
            System.out.println();
            System.out.println("- - - - - - - - - -");
        }
        System.out.print("\n");
    }

    /**
     * allow to get every Vulnerable Station
     * @return every Vulnerable Station
     */
    public ArrayList<RentStation<T>> getVulnerableStation(){
        ArrayList<RentStation<T>> vStation = new ArrayList<>();
        for(RentStation<T> station : theStations){
            if(station.isVulnerable()){
                vStation.add(station);
            }
        }
        return vStation;
    }

    public ArrayList<Vehicle> getVulnerableVehicles(){
        ArrayList<Vehicle> vulVehicles = new ArrayList<>();
        ArrayList<RentStation<T>> vStation = getVulnerableStation();

        for(RentStation<?> station: vStation){
            vulVehicles.addAll(station.getAllVehicles());
        }
        return vulVehicles;
    }

    /**
     * Allow to steal a Vehicle in the ControlCenter
     * @param v the Vehicle which is stolen
     */
    public void stealVehicle(Vehicle v){
        v.setState(new StolenState());
        this.theStolenVehicle.add(v);
    }
}
