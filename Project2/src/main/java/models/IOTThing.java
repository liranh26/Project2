package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class IOTThing extends Hardware {

	private Map<String, Device> devices;
	private final static int SENSOR_RANDOM_RANGE = 20;

	public IOTThing(HardwareType type, String model, String manufacturer) {
		super(type, model, manufacturer);
		devices = new HashMap<String, Device>();
	}

	
	public void setListDevices(List<Device> devicesList) {
		devicesList.stream().forEach(d -> devices.put(d.getID(), d));
	}

	public void addDevice(Device device) {
		devices.put(device.getID(), device);
	}

	public Map<String, Device> getDevices() {
		return devices;
	}

	public void simulateInventoryChange() {
		Random rand = new Random();
		List<Device> listDevices = new ArrayList<Device>(devices.values());
		if(rand.nextInt(2) == 1 && listDevices.size() > 1)
			devices.values().remove(listDevices.get(rand.nextInt(listDevices.size())));

		devices.values().stream().forEach(d -> {
			d.simulateMeasure(Math.random() * SENSOR_RANDOM_RANGE);
		});
	}

	public void setDevices(Map<String, Device> devices) {
		this.devices = devices;
	}

	public static int getSensorRandomRange() {
		return SENSOR_RANDOM_RANGE;
	}

	@Override
	public String toString() {
		return "IOTThing " + super.toString() + "[devices=" + devices + "]";
	}

	public static void main(String[] args) {
		IOTThing iot = new IOTThing(HardwareType.CONTROLLER, "14AT", "Controllers");
		iot.addDevice(new Device(HardwareType.ACTUATOR, "1234A", "Reno-Gear"));
		iot.addDevice(new Device(HardwareType.SENSOR, "2292", "Sensor"));
		iot.addDevice(new Device(HardwareType.CONTROLLER, "ZX88", "Controllers"));
		System.out.println(iot);
	}

}
