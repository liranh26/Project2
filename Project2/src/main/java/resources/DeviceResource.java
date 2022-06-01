package resources;

import java.util.List;

import dbService.DBService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import models.Device;

@Path("devices")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DeviceResource {
	
	DBService iotDBservice = new DBService();
	
	@GET
	public List<Device> getAllIOTThings() {
		return iotDBservice.getAllDevices();
	}
	
}
