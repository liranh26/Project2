package project2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import inventory.webservice.rest.app.models.Device;
import inventory.webservice.rest.app.models.HardwareType;
import inventory.webservice.rest.app.models.IOTThing;

class IOTThingTest {

	IOTThing iot;
	Device device1 = new Device(HardwareType.ACTUATOR, "1234A", "Reno-Gear");
	Device device2 = new Device(HardwareType.SENSOR, "2292", "Sensor");
	Device device3 = new Device(HardwareType.CONTROLLER, "ZX88", "Controllers");
	Map<String, Device> devices;
	
	IOTThingTest(){
		iot = new IOTThing(HardwareType.SENSOR, "ZX33", "HomeCare");
		
		iot.addDevice(device1);
		iot.addDevice(device2);
		
		devices = new HashMap<String, Device>();
		devices.put(device1.getID(), device1);
		devices.put(device2.getID(), device2);
	}
	
	@Test
	void testAddDeviceToMap() {
		assertFalse(iot.getDevices().isEmpty());
		assertEquals(device1.getID(), iot.getDevices().get(device1.getID()).getID());
		assertEquals(device2.getID(), iot.getDevices().get(device2.getID()).getID());
		assertNull(iot.getDevices().get(device3.getID()));
	}
	
	@Test
	void testSettingMap() {
		iot.setDevices(devices);
		assertFalse(iot.getDevices().isEmpty());
		assertEquals(device1.getID(), iot.getDevices().get(device1.getID()).getID());
		assertEquals(device2.getID(), iot.getDevices().get(device2.getID()).getID());
		assertNull(iot.getDevices().get(device3.getID()));
	}
	
	
	@Test
	void testHardwareType() {
		assertEquals(HardwareType.SENSOR, iot.getType());
		iot.setType(HardwareType.CONTROLLER);
		assertEquals(HardwareType.CONTROLLER, iot.getType());
	}
	
	@Test
	void testDeviceModel() {
		assertEquals("ZX33", iot.getModel());
		iot.setModel("4321");
		assertEquals("4321", iot.getModel());
	}
	
	@Test
	void testDeviceManufacturer() {
		assertEquals("HomeCare", iot.getManufacturer());
		iot.setManufacturer("4321");
		assertEquals("4321", iot.getManufacturer());
	}

}
