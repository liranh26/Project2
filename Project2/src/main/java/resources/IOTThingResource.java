package resources;

import java.util.ArrayList;
import java.util.List;

import beans.IOTThingFilterBean;
import dbService.DBService;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import models.IOTThing;

@Path("inventory")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IOTThingResource {

	DBService iotDBservice = new DBService();

//	@GET
//	public List<IOTThing> getAllIOTThings() {
//		return iotDBservice.getAllIOTThings();
//	}

	@GET
	public List<IOTThing> getAllIOTThings(@BeanParam IOTThingFilterBean iotFilter) {

	}

}
