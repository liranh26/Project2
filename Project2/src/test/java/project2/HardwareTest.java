package project2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import inventory.webservice.rest.app.models.HardwareType;

class HardwareTest {
	
	String actuatorType = "ACTUATOR";
	String sensorType = "SENSOR";
	String controllerType = "CONTROLLER";
	
	@Test
	void testHardwareTypes() {
		assertTrue(actuatorType.equals(HardwareType.ACTUATOR.toString()));
		assertTrue(sensorType.equals(HardwareType.SENSOR.toString()));
		assertTrue(controllerType.equals(HardwareType.CONTROLLER.toString()));
	}

}
