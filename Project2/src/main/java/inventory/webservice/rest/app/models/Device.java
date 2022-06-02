package inventory.webservice.rest.app.models;

public class Device extends Hardware{
	
	private double sensorMeasure;

	public Device(HardwareType type, String model, String manufacturer) {
		super(type, model, manufacturer);
	}

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
