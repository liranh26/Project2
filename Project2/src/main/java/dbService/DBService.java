package dbService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import DB.DBMock;
import models.Device;
import models.IOTThing;

public class DBService {

	private DBMock db;
	private Map<String, IOTThing> things;
	private Map<String, Device> devices;
	
	public DBService() {
		db = DBMock.getInstance();
		things = db.getIOTThings();
		devices = db.getDevices();
	}

	public List<IOTThing> getAllIOTThings(){
		return new ArrayList<IOTThing>(things.values());
	}
	
	public void updateServer(IOTThing thing) {
		things.put(thing.getID(), thing);
		updateDevices(thing);
		
//		System.out.println(things);
		System.out.println(devices);
	}
	
	private void updateDevices(IOTThing thing) {
		thing.getDevices().values().forEach(device -> {
			devices.put(device.getID(), device);
		});
	}
	
}
