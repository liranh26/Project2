package project2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import inventory.webservice.rest.app.beans.IOTThingFilterBean;
import inventory.webservice.rest.app.dbService.IOTThingDBService;
import inventory.webservice.rest.app.exceptions.MissingDataException;
import inventory.webservice.rest.app.models.HardwareType;
import inventory.webservice.rest.app.resources.IOTThingResource;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

class IOTThingResourceTest {

	private IOTThingResource resource;
	
	IOTThingResourceTest(){
		this.resource = new IOTThingResource();
	}

	
	@Test
	void getAllThingsTest() {
		
		IOTThingFilterBean iotFilter = new IOTThingFilterBean();
		
		Response actual = resource.getAllIOTThings(iotFilter);
		assertEquals(Status.ACCEPTED.getStatusCode(), actual.getStatus());

	}
	
	@Test
	void getThingByID() {
		
		IOTThingDBService service = new IOTThingDBService();
		//DB is seeded with objects
		Response actual = resource.getIOTThingById(service.getAllIOTThings().stream().toList().get(0).getID());
		assertEquals(Status.OK.getStatusCode(), actual.getStatus());
		
		boolean exceptionCaught = false;
		try {
			Response actualWithException = resource.getIOTThingById(service.getAllIOTThings().stream().toList().get(0).getID()+"blabla");
		}catch(MissingDataException e) {
			exceptionCaught = true;
		}catch(Exception e) {
			exceptionCaught = true;
			System.err.println(e + ", could not catch MissingDataException!");
		}
		assertTrue(exceptionCaught);
	}
	
	@Test
	void getThingByProperties() {
		
		IOTThingFilterBean iotFilter = new IOTThingFilterBean();
		iotFilter.setModel("8001");
		iotFilter.setManufacturer("HomeControllers");
		//check first only half input
		boolean exceptionCaught = false;
		try {
		Response actual = resource.getAllIOTThings(iotFilter);
		}catch(MissingDataException e) {
			exceptionCaught = true;
		}catch(Exception e) {
			exceptionCaught = true;
			System.err.println(e + ", could not catch MissingDataException!");
		}
		assertTrue(exceptionCaught);
		
		//check valid complete input
		iotFilter.setType(HardwareType.CONTROLLER);
		
		Response actual = resource.getAllIOTThings(iotFilter);
		assertEquals(Status.CREATED.getStatusCode(), actual.getStatus());

	}

	
	
}
