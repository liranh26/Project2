package inventory.webservice.rest.app.dbService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import inventory.webservice.rest.app.DB.DBMock;
import inventory.webservice.rest.app.beans.IOTThingFilterBean;
import inventory.webservice.rest.app.exceptions.MissingDataException;
import inventory.webservice.rest.app.models.Device;
import inventory.webservice.rest.app.models.IOTThing;

public class IOTThingDBService {

	private DBMock db;
	private Map<String, IOTThing> things;
	private Map<String, Device> devices;

	public IOTThingDBService() {
		db = DBMock.getInstance();
		things = db.getIOTThings();
		devices = db.getDevices();
	}

	
	public List<IOTThing> getAllIOTThings() {
		return new ArrayList<IOTThing>(things.values());
	}

	
	public List<Device> getAllDevices() {
		return new ArrayList<Device>(devices.values());
	}
	

	public void updateToDB(IOTThing thing) {

		if (!things.containsKey(thing.getID()))
			addToDB(thing);
		else {
			updateDevices(thing);
			things.put(thing.getID(), thing);
		}
		System.out.println("DB devices list size --> " + devices.values().size());
	}

	
	private void addToDB(IOTThing thing) {
		things.put(thing.getID(), thing);
		thing.getDevices().values().stream().forEach(device -> devices.put(device.getID(), device));
	}

	
	private void updateDevices(IOTThing thing) {

		List<Device> oldDevices = new ArrayList<Device>(things.get(thing.getID()).getDevices().values());
		List<Device> newDevices = new ArrayList<Device>(thing.getDevices().values());
		// create new map to check if all keys(devices) from before still exist
		Map<String, Device> newDeviceMap = newDevices.stream()
				.collect(Collectors.toMap(Device::getID, Function.identity()));

		oldDevices.stream().forEach(d -> {
			if (newDeviceMap.containsKey(d.getID()))
				devices.put(d.getID(), newDeviceMap.get(d.getID()));
			else
				devices.remove(d.getID());
		});
	}
	
	

	public IOTThing getIOTThingById(String id) {
		IOTThing responseIOT = things.get(id);
		if (responseIOT == null)
			throw new MissingDataException("This id doesn't exist at the DB!");
		return things.get(id);
	}

	
	
	public List<IOTThing> getThingsByProperties(IOTThingFilterBean iotFilter) {

		if (iotFilter.missingData())
			throw new MissingDataException("Missing data of requested IOT Thing!");

		List<IOTThing> responseList = getAllIOTThings().stream()
				.filter(iot -> (iot.getModel().equals(iotFilter.getModel())
						&& iot.getManufacturer().equals(iotFilter.getManufacturer())
						&& iot.getType().equals(iotFilter.getType())))
				.collect(Collectors.toList());

		if (responseList.isEmpty())
			throw new MissingDataException("We could not find a match for you - try other inputs!");

		return responseList;
	}

}