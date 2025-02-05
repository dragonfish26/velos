package simVlille.time;

import simVlille.humans.Repairman;
import simVlille.humans.Thief;
import simVlille.station.RentStation;
import simVlille.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.Random;

public class TimeManager {

    private int totalInterval;
	private ControlCenter<?> controlCenter;
	private final Random random = new Random();
	private Thief thief;
	
	/**
	 * Class Constructor
	 */
	public TimeManager() {
		//this.thief = new Thief(controlCenter);
	}
	
	/** Set a ControlCenter to a TimeManager
	 * @param c the ControlCenter to be assigned to this timeManager
	 */
	public void setControlCenter(ControlCenter<?> c) {
		this.controlCenter = c;
		this.thief = new Thief(controlCenter);
	}
	
	/** Set the number of total intervals
	 * @param t the number of total intervals
	 */
	public void setTotalInterval(int t) {
		this.totalInterval = t;
	}
	
	/**
	 * All the actions to be executed during one interval is executed in this method
	 */
	public void flowForOneInterval() {

		//First rent random number of vehicles
		rentForOneFlow();

		//Second a random number of vehicles are returned
		returnForOneFlow();

		//Third check if redistribution needs to happen
		boolean needToRedistribute = checkForRedistribution();


		// if redistribution is needed, redistribute
		if (needToRedistribute) {
			controlCenter.redistribute();
			System.out.println("Redistribution is executed.");

			//update the attributes that indicate if redistribution is needed for each station
			for (RentStation<?> station : controlCenter.getStations()) {
                station.updateOk();
			}
		}

		// repair broken vehicles
		repairVehicles();

		//the thief steals vehicles
		this.stealVehicles();
	}

	/**
	 * Allow to steal a Vehicle
	 */
	public void stealVehicles(){
		System.out.println("---Theft:");
		//update if the stations are vulnerable
		for (RentStation<?> rs: controlCenter.getStations()){
			rs.handleHasOneVehicle();
		}
		ArrayList<Vehicle> vulVehicles = this.controlCenter.getVulnerableVehicles();

		for(Vehicle vehicle: vulVehicles){
			vehicle.accept(thief);
			this.controlCenter.stealVehicle(vehicle);
		}
	}
	
	/**
	 * Runs the time or executes the flow for a specified amount of intervals
	 */
	public void startFlow() {

		for (RentStation rs : controlCenter.getStations()){
			rs.addObserver(controlCenter);
		}

        int currentInterval;
        for (currentInterval = 0; currentInterval <= this.totalInterval; currentInterval++) {
			try {
				System.out.println("- - - - - - - - - - - - - - - -");
				System.out.print("\nINTERVAL " + currentInterval + "\n");
				flowForOneInterval();
				//this.controlCenter.display();
				System.out.println("- - - - - - - - - - - - - - - -");
				// Pause the loop for 1.5 seconds (1500 milliseconds)
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// Handle exception if sleep is interrupted
				System.err.println("The sleep was interrupted.");
			}
		}
	}

	/** A method to rent a random number of vehicles among all the available vehicles
	 * within the stations managed by the control station
	 * the random number range is the number of all available vehicles
	 */
	public void rentForOneFlow(){
		System.out.print("---Rents:\n");
		int range = this.controlCenter.getAvailableVehicles().size();
		int nbStations = controlCenter.getStations().size();
		if (range > 0){
			int randomNbRents = random.nextInt(range);
			for (int i=0; i<randomNbRents; i++){
				RentStation<?> randomStation = controlCenter.getStations().get(random.nextInt(nbStations));
				rentVehicle(randomStation);
			}
		}
	}

	/** A method to return a random number of vehicles within all the vehicles
	 * that are currently being rented
	 */
	public void returnForOneFlow(){
		System.out.print("---Returns:\n");
		int nbRentedVehicles = controlCenter.getTheRentalVehicles().size();
		if (nbRentedVehicles >0){
			int randomNbReturns = random.nextInt(nbRentedVehicles);
			for (int i=0; i<randomNbReturns; i++) {
				//get a random vehicle from the list of rented vehicle
				int randomNb = random.nextInt(controlCenter.getTheRentalVehicles().size());
				Vehicle v = (Vehicle) controlCenter.getTheRentalVehicles().get(randomNb);
				returnVehicle(v);
			}
		}
	}

	/**
	 * A method to rent a random vehicle from a station
	 *
	 * @param rs The station to rent from
	 */
	public void rentVehicle(RentStation<?> rs){
		//Vehicle v = rs.rentRandomVehicle();
		if (!rs.getAvailableVehicles().isEmpty()){
			Vehicle v = rs.rentRandomVehicle();
			controlCenter.addToRentalVehicles(v); // add the rented vehicle to the list of rented vehicles
			//System.out.print(v.getDescription()+" "+v.getId()+ " from S" + rs.getId() + "\n");
			rs.notifyObserver(v);
		}
	}


	/**
	 * A method to return a vehicle to a random station
	 *
	 * @param v The vehicle to return
	 */
	public void returnVehicle(Vehicle v){
		int nbStations = controlCenter.getStations().size();
		int i = random.nextInt(nbStations);
		RentStation rs = controlCenter.getStations().get(i);
		if(rs.stillHavePlace()){
			rs.returnVehicle(v);
			controlCenter.removeFromRentalVehicles(v); // remove the returned vehicle from the rented vehicles
			//System.out.print(v.getDescription()+" "+v.getId()+" to S" + rs.getId() + "\n");
			rs.notifyObserver(v);
		}
	}

	/**
	 * A method to if redistribution is needed
	 * @return True if redistribution is needed, false otherwise
	 */
	public boolean checkForRedistribution(){
		boolean res = false;
		for (RentStation<?> rs: controlCenter.getStations()){
			rs.updateOk();
			if (rs.needsToRedistribute()){
				res = true;
			}
		}
		return res;
	}

	/*
	A method to repair broken vehicles if there is one
	The repairman of the control center repairs one vehicle at a time
	 */
	public void repairVehicles(){
		System.out.println("---Repairment:");
		Repairman repairman = controlCenter.getRepairman();
		if (!controlCenter.getBrokenVehicles().isEmpty() && !repairman.isBusy()) {
			Vehicle brokenVehicle = controlCenter.getBrokenVehicles().getFirst();
			brokenVehicle.accept(repairman);
			//System.out.println("Repairing: vehicle " + brokenVehicle.getId());
		}

		if(repairman.isBusy()){
			System.out.println("Repairing:  V" + repairman.getRepairingVehicle().getId());
			repairman.repairVehicle();

		}
	}
}
