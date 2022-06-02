package inventory.webservice.rest.app.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IOTThing extends Hardware {

	private Map<String, Device> devices;
	private final static int SENSOR_RANDOM_RANGE = 20;
	private final int MIN_DEVICES = 1;
	private final int FIRST_DEVICE_REMOVE = 0;

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

		List<Device> listDevices = new ArrayList<Device>(devices.values());
		if(listDevices.size() > MIN_DEVICES)
			devices.values().remove(listDevices.get(FIRST_DEVICE_REMOVE));

		devices.values().stream().forEach(d -> {
			d.setSensorMeasure(Math.random() * SENSOR_RANDOM_RANGE);
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

}
