package project2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import inventory.webservice.rest.app.beans.DevicesFilterBean;
import inventory.webservice.rest.app.dbService.DeviceDBService;
import inventory.webservice.rest.app.exceptions.MissingDataException;
import inventory.webservice.rest.app.models.Device;
import inventory.webservice.rest.app.models.IOTThing;
import inventory.webservice.rest.app.resources.DeviceResource;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

class DeviceResourceTest {

	private DeviceResource resource;
	private DeviceDBService service;
	
	DeviceResourceTest(){
		this.resource  = new DeviceResource();
		this.service = new DeviceDBService();
	}
	
	@Test
	void getAllDevicesTest() {
		DevicesFilterBean deviceFilter = new DevicesFilterBean();
		
		Response actual = resource.getDevices(deviceFilter);
		assertEquals(Status.ACCEPTED.getStatusCode(), actual.getStatus());
	}
	
	@Test
	void getDeviceByID() {
		
		
		//DB is seeded with objects
		Response actual = resource.getDeviceById(service.getAllDevices().stream().toList().get(0).getID());
		assertEquals(Status.OK.getStatusCode(), actual.getStatus());
		
		boolean exceptionCaught = false;
		try {
			Response actualWithException = resource.getDeviceById(service.getAllDevices().stream().toList().get(0).getID()+"blabla");
		}catch(MissingDataException e) {
			exceptionCaught = true;
		}catch(Exception e) {
			exceptionCaught = true;
			System.err.println(e + ", could not catch MissingDataException!");
		}
		assertTrue(exceptionCaught);
	}
	
	
	@Test
	void getDeviceByProperties() {
		
		IOTThing iotTest = service.getAllIOTThings().get(0);
		List<Device> iotTestDevices = iotTest.getDevices().values().stream().toList();
		
		DevicesFilterBean deviceFilter = new DevicesFilterBean();
		deviceFilter.setModel(iotTestDevices.get(0).getModel());
		deviceFilter.setManufacturer(iotTestDevices.get(0).getManufacturer());
		
		//check only half input
		boolean exceptionCaught = false;
		try {
			Response actual = resource.getDevices(deviceFilter);
		}catch(MissingDataException e) {
			exceptionCaught = true;
		}catch(Exception e) {
			exceptionCaught = true;
			System.err.println(e + ", could not catch MissingDataException!");
		}
		assertTrue(exceptionCaught);
		
		//check valid complete input
		deviceFilter.setType(iotTestDevices.get(0).getType());
		deviceFilter.setId(iotTest.getID());
		
		Response actual = resource.getDevices(deviceFilter);
		assertEquals(Status.CREATED.getStatusCode(), actual.getStatus());

	}

	
}
