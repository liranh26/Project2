package DB;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import client.IOTThing;
import models.Device;
import models.HardwareType;

public class DBMock {

	private static DBMock instance = null;
	private Map<String, IOTThing> things;

	public static synchronized DBMock getInstance() {
		if (instance == null)
			instance = new DBMock();
		return instance;
	}

	private DBMock() {
		things = new HashMap<String, IOTThing>();
		// seeding the db
		seedIOTThing();
	}

	private void seedIOTThing() {
		List<Device> deviceList = Arrays.asList(new Device(HardwareType.ACTUATOR, "1234A", "Reno-Gear"),
				new Device(HardwareType.SENSOR, "2292", "Sensor"),
				new Device(HardwareType.CONTROLLER, "ZX88", "Controllers"));

		List<IOTThing> iotThingList = Arrays.asList(new IOTThing(HardwareType.CONTROLLER, "8001", "HomeControllers"),
				new IOTThing(HardwareType.CONTROLLER, "1000", "HomeControllers"),
				new IOTThing(HardwareType.SENSOR, "5000", "HomeSensors"));

		iotThingList.stream().forEach(iot -> iot.setDevices(deviceList));

		things = iotThingList.stream().collect(Collectors.toMap(IOTThing::getID, Function.identity()));
	}

	public Map<String, IOTThing> getStudents() {
		return things;
	}

}
