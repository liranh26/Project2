package project2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import inventory.webservice.rest.app.beans.IOTThingFilterBean;
import inventory.webservice.rest.app.dbService.IOTThingDBService;
import inventory.webservice.rest.app.models.HardwareType;
import inventory.webservice.rest.app.resources.IOTThingResource;
import jakarta.ws.rs.core.Response;

class IOTThingResourceTest {

	private IOTThingResource resource;
	
//	@BeforeEach
	IOTThingResourceTest(){
		this.resource = new IOTThingResource();
	}

	
	@Test
	void getAllThingsTest() {
		
		IOTThingFilterBean iotFilter = new IOTThingFilterBean();
		
		Response actual = resource.getAllIOTThings(iotFilter);
		
		assertEquals(202, actual.getStatus());
	}
	
	@Test
	void getThingByID() {
		
		IOTThingDBService service = new IOTThingDBService();
		
		Response actual = resource.getIOTThingById(service.getAllIOTThings().stream().toList().get(0).getID());
		
		assertEquals(200, actual.getStatus());
	}

	
//	@Test
//	void getMissingDataExceptionTest() {
//		
//		IOTThingFilterBean iotFilter = new IOTThingFilterBean();
//		iotFilter.setManufacturer("HomeControllers");
//		iotFilter.setModel("8001");
//		iotFilter.setType(HardwareType.SENSOR);
//		
//		Response actual = resource.getAllIOTThings(iotFilter);
//		
//		assertEquals(404, actual.getStatus());
//	}
	
	
	
	
	
}
