package dbService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import DB.DBMock;
import models.Device;
import models.IOTThing;

public class IOTThingDBService {

	private DBMock db;
	private Map<String, IOTThing> things;
	private Map<String, Device> devices;
	
	public IOTThingDBService() {
		db = DBMock.getInstance();
		things = db.getIOTThings();
		devices = db.getDevices();
	}

	public List<IOTThing> getAllIOTThings(){
		return new ArrayList<IOTThing>(things.values());
	}
	
	public List<Device> getAllDevices(){
		return new ArrayList<Device>(devices.values());
	}
	
	
	public void updateToDB(IOTThing thing) {
		
		if(!things.containsKey(thing.getID()))
			addToDB(thing);
		else {
			updateDevices(thing);
			things.put(thing.getID(), thing);
		}
		System.out.println("DB devices list size --> " + devices.values().size());
		System.out.println(devices);
	}
	
	private void addToDB(IOTThing thing) {
		things.put(thing.getID(), thing);
		thing.getDevices().values().stream().forEach(device -> devices.put(device.getID(), device));
	}

	
	private void updateDevices(IOTThing thing) {
		
		List<Device> oldDevices = new ArrayList<Device>(things.get(thing.getID()).getDevices().values());
		List<Device> newDevices = new ArrayList<Device>(thing.getDevices().values());
		//create new map to check if all keys(devices) from before still exist
		Map<String, Device> newDeviceMap = newDevices.stream().collect(Collectors.toMap(Device::getID, Function.identity()));
		
		oldDevices.stream().forEach(d -> {
			if(newDeviceMap.containsKey(d.getID()))
				devices.put(d.getID(), newDeviceMap.get(d.getID()));
			else
				devices.remove(d.getID());
		});
		
//		for (int i = 0; i < oldDevices.size(); i++) {
//			
//			if(newDeviceMap.containsKey(oldDevices.get(i).getID()))
//				devices.put(newDevices.get(i).getID(), newDevices.get(i));
//			else
//				devices.remove(oldDevices.get(i).getID());
//		}
	}

}