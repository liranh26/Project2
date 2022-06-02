package inventory.webservice.rest.app.models;

public class Device extends Hardware{
	
	private double sensorMeasure;

//	public Device() {
//		super(HardwareType.ACTUATOR, "1234A", "Reno-Gear");
//	}

	public Device(HardwareType type, String model, String manufacturer) {
		super(type, model, manufacturer);
	}
	
//	public double getSensorRead() {
//		return sensorMeasure;
//	}

	//simulate an input from the sensor
//	public void simulateMeasure(double sensorRead) {
//		this.sensorMeasure = sensorRead;
//	}
	

	public double getSensorMeasure() {
		return sensorMeasure;
	}

	public void setSensorMeasure(double sensorMeasure) {
		this.sensorMeasure = sensorMeasure;
	}

	@Override
	public String toString() {
		return "Device [sensorMeasure=" + sensorMeasure + "] " + super.toString() +"\n";
	}


	
}
