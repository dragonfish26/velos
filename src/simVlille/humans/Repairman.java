package simVlille.humans;

import simVlille.time.ControlCenter;
import simVlille.vehicle.Vehicle;
import simVlille.vehicle.state.AvailableState;

/* A class for type Repairman*/
public class Repairman implements Visitor {

	private ControlCenter<?> controlCenter;
	private Vehicle repairingVehicle;
	private boolean isBusy;

	//an attribute to count the interval of time spent to Repair
	private int timeSpentRepairing;

	public Repairman(ControlCenter<?> controlCenter){
		this.timeSpentRepairing = 0;
		this.repairingVehicle = null;
		this.isBusy = false;
		this.controlCenter = controlCenter;
	}

	@Override
	public void visit(Vehicle v){
		//this.timeSpentRepairing++;
		this.repairingVehicle = v;
		this.isBusy = true;

	}

	public void repairVehicle(){
		this.timeSpentRepairing++;

		if (this.timeSpentRepairing>= 2){

			System.out.println("Repaired : V" + repairingVehicle.getId());
			repairingVehicle.setState(new AvailableState());
			repairingVehicle.setUsageCount(0);
			//controlCenter.removeFromBrokenVehicle(repairingVehicle);

			this.timeSpentRepairing = 0;
			this.repairingVehicle = null;
			this.isBusy = false;
		}

	}

	public boolean isBusy(){
		return this.isBusy;
	}

	public Vehicle getRepairingVehicle(){
		return this.repairingVehicle;
	}


}
