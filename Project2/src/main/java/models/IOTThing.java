package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IOTThing extends Hardware{

	private Map<String, Device> devices;
	private final static int SENSOR_RANDOM_RANGE=20;

//	public IOTThing() {
//		super();
//	}
	
	public IOTThing(HardwareType type, String model, String manufacturer) {
		super(type, model, manufacturer);
		initDevices();
	}

	
	public void setDevices(List<Device> devicesList) {
		for (Device device : devicesList) {
			this.devices.put(device.getID(), device);
		}
	}
	
	public Map<String, Device> getDevices() {
		return devices;
	}

	
	public void simulateInventoryChange() {
		devices.values().stream().forEach(d -> {
			d.simulateMeasure(Math.random() * SENSOR_RANDOM_RANGE);
		});
	}
	
	

	private void initDevices() {
		devices = new HashMap<>();
		Device device = new Device(HardwareType.ACTUATOR, "1234A", "Reno-Gear");
		devices.put(device.getID(), device);
	}
	

	@Override
	public String toString() {
		return "IOTThing " +super.toString() +"\n[devices=" + devices + "]";
	}
	
	
//	public static void main(String[] args) {
//		IOTThing iot = new IOTThing(HardwareType.CONTROLLER, "14AT", "Controllers");
//		System.out.println(iot);
//		iot.simulateInventoryChange();
//		System.out.println("-------------");
//		System.out.println(iot);
//	}
	
}
