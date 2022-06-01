package dbService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import DB.DBMock;
import models.Device;
import models.IOTThing;

public class DeviceDBService {

	private DBMock db;
	private Map<String, IOTThing> things;
	private Map<String, Device> devices;
	
	public DeviceDBService() {
		db = DBMock.getInstance();
		things = db.getIOTThings();
		devices = db.getDevices();
	}
	
	public List<Device> getAllDevices(){
		return new ArrayList<Device>(devices.values());
	}

}
