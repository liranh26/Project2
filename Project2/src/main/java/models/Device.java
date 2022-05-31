package models;

public class Device extends Hardware{
	
	private double sensorMeasure;

	public Device() {
		super(HardwareType.ACTUATOR, "1234A", "Reno-Gear");
	}

	public Device(HardwareType type, String model, String manufacturer) {
		super(type, model, manufacturer);
	}
	
	public double getSensorRead() {
		return sensorMeasure;
	}

	//simulate an input from the sensor
	public void simulateMeasure(double sensorRead) {
		this.sensorMeasure = sensorRead;
	}

	@Override
	public String toString() {
		return "Device [last sensor measure: " + sensorMeasure + "]" + super.toString();
	}
	
}
