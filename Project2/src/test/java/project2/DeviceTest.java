package project2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import models.Device;
import models.HardwareType;

class DeviceTest {

	Device device;
	
	public DeviceTest() {
		device = new Device(HardwareType.ACTUATOR, "1234A", "Reno-Gear");
	}
	
	@Test
	void testSensorMeaureField() {
		double testValue = 25.5;
		device.setSensorMeasure(testValue);
		assertEquals(testValue, device.getSensorMeasure());
	}
	
	@Test
	void testHardwareType() {
		assertEquals(HardwareType.ACTUATOR, device.getType());
		device.setType(HardwareType.CONTROLLER);
		assertEquals(HardwareType.CONTROLLER, device.getType());
	}
	
	@Test
	void testDeviceModel() {
		assertEquals("1234A", device.getModel());
		device.setModel("4321");
		assertEquals("4321", device.getModel());
	}
	
	@Test
	void testDeviceManufacturer() {
		assertEquals("Reno-Gear", device.getManufacturer());
		device.setManufacturer("4321");
		assertEquals("4321", device.getManufacturer());
	}

}
