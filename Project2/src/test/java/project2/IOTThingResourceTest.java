package project2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import inventory.webservice.rest.app.beans.IOTThingFilterBean;
import inventory.webservice.rest.app.dbService.IOTThingDBService;
import inventory.webservice.rest.app.exceptions.MissingDataException;
import inventory.webservice.rest.app.resources.IOTThingResource;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

class IOTThingResourceTest {

	private IOTThingResource resource;
	private IOTThingDBService service;

	IOTThingResourceTest() {
		this.resource = new IOTThingResource();
		this.service = new IOTThingDBService();
	}

	@Test
	void getAllThingsTest() {

		IOTThingFilterBean iotFilter = new IOTThingFilterBean();

		Response actual = resource.getIOTThings(iotFilter);
		assertEquals(Status.ACCEPTED.getStatusCode(), actual.getStatus());

	}

	@Test
	void getThingByID() {

		// DB is seeded with objects
		Response actual = resource.getIOTThingById(service.getAllIOTThings().stream().toList().get(0).getID());
		assertEquals(Status.OK.getStatusCode(), actual.getStatus());

		boolean exceptionCaught = false;
		try {
			Response actualWithException = resource
					.getIOTThingById(service.getAllIOTThings().stream().toList().get(0).getID() + "blabla");
		} catch (MissingDataException e) {
			exceptionCaught = true;
		} catch (Exception e) {
			exceptionCaught = true;
			System.err.println(e + ", could not catch MissingDataException!");
		}
		assertTrue(exceptionCaught);
	}

	@Test
	void getThingByProperties() {

		IOTThingFilterBean iotFilter = new IOTThingFilterBean();
		iotFilter.setModel(service.getAllIOTThings().get(0).getModel());
		iotFilter.setManufacturer(service.getAllIOTThings().get(0).getManufacturer());

		// check only half input
		boolean exceptionCaught = false;
		try {
			Response actual = resource.getIOTThings(iotFilter);
		} catch (MissingDataException e) {
			exceptionCaught = true;
		} catch (Exception e) {
			exceptionCaught = true;
			System.err.println(e + ", could not catch MissingDataException!");
		}
		assertTrue(exceptionCaught);

		// check valid complete input
		iotFilter.setType(service.getAllIOTThings().get(0).getType());

		Response actual = resource.getIOTThings(iotFilter);
		assertEquals(Status.CREATED.getStatusCode(), actual.getStatus());

	}

}
