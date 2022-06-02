package inventory.webservice.rest.app.dbService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import inventory.webservice.rest.app.DB.DBMock;
import inventory.webservice.rest.app.beans.DevicesFilterBean;
import inventory.webservice.rest.app.exceptions.MissingDataException;
import inventory.webservice.rest.app.models.Device;
import inventory.webservice.rest.app.models.IOTThing;

/*
 * DeviceDBService manages the data of the DB.
 * serve data from the DB, in case of invalid inputs throws MissingDataException with a custom error message.
 */

public class DeviceDBService {

	private DBMock db;
	private Map<String, IOTThing> things;
	private Map<String, Device> devices;

	public DeviceDBService() {
		db = DBMock.getInstance();
		things = db.getIOTThings();
		devices = db.getDevices();
	}

	public List<Device> getAllDevices() {
		return new ArrayList<Device>(devices.values());
	}

	public List<IOTThing> getAllIOTThings() {
		return new ArrayList<IOTThing>(things.values());
	}

	public Device getDeviceById(String id) {
		if (devices.get(id) == null)
			throw new MissingDataException("This id doesn't exist at the DB!");
		return devices.get(id);
	}

	public List<Device> getDevicesByPropertiesFromThing(DevicesFilterBean deviceFilter) {

		if (deviceFilter.missingData())
			throw new MissingDataException("Missing data of requested IOT Thing!");

		List<Device> responseList = things.get(deviceFilter.getIOTThingId()).getDevices().values().stream()
				.filter(iot -> (iot.getModel().equals(deviceFilter.getModel())
						&& iot.getManufacturer().equals(deviceFilter.getManufacturer())
						&& iot.getType().equals(deviceFilter.getType())))
				.collect(Collectors.toList());

		if (responseList.isEmpty())
			throw new MissingDataException("We could not find a match for you - try other inputs!");

		return responseList;
	}

}
