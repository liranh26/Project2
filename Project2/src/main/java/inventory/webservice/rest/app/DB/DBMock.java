package inventory.webservice.rest.app.DB;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import inventory.webservice.rest.app.models.Device;
import inventory.webservice.rest.app.models.HardwareType;
import inventory.webservice.rest.app.models.IOTThing;

/*
 * DBMock simulate a DB which holds the inventory data.
 */

public class DBMock {

	private static DBMock instance = null;
	private Map<String, IOTThing> things;
	private Map<String, Device> devices;
	private final int SENSOR_RANDOM_RANGE = 50;
	
	public static synchronized DBMock getInstance() {
		if (instance == null)
			instance = new DBMock();
		return instance;
	}

	private DBMock() {
		things = new HashMap<String, IOTThing>();
		devices = new HashMap<String, Device>();
		seedIOTThing();
		setDeviceMap();
	}

	private void seedIOTThing() {
		List<Device> deviceList = Arrays.asList(new Device(HardwareType.ACTUATOR, "1234A", "Reno-Gear"),
				new Device(HardwareType.SENSOR, "2292", "Sensor"),
				new Device(HardwareType.CONTROLLER, "ZX88", "Controllers"));

		deviceList.stream().forEach(d -> d.setSensorMeasure(Math.random() * SENSOR_RANDOM_RANGE));
		
		List<IOTThing> iotThingList = Arrays.asList(new IOTThing(HardwareType.CONTROLLER, "8001", "HomeControllers"),
				new IOTThing(HardwareType.CONTROLLER, "1000", "HomeControllers"),
				new IOTThing(HardwareType.SENSOR, "5000", "HomeSensors"),
				new IOTThing(HardwareType.CONTROLLER, "14AT", "Controllers"));

		iotThingList.stream().forEach(iot -> iot.setListDevices(deviceList));

		things = iotThingList.stream().collect(Collectors.toMap(IOTThing::getID, Function.identity()));
	}
	
	private void setDeviceMap() {
		things.values().stream().forEach(thing->{
			thing.getDevices().values().stream().forEach(device -> devices.put(device.getID(), device));
		});
	}

	public Map<String, IOTThing> getIOTThings() {
		return things;
	}
	
	public Map<String, Device> getDevices() {
		return devices;
	}

}
