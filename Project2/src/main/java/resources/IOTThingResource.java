package resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import beans.IOTThingFilterBean;
import dbService.IOTThingDBService;
import exceptions.MissingDataException;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriInfo;
import models.IOTThing;

@Path("inventory")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IOTThingResource {

	IOTThingDBService iotDBservice = new IOTThingDBService();

	@GET
	public Response getAllIOTThings(@BeanParam IOTThingFilterBean iotFilter) throws MissingDataException {

		Status status = Status.NOT_FOUND;
		
		List<IOTThing> responseList = new ArrayList<IOTThing>();

		System.out.println(iotDBservice.getAllDevices());
		
		if (iotFilter.isEmpty()) {
			status = Status.ACCEPTED;  //TODO --> which code needed here ???
			responseList = iotDBservice.getAllIOTThings();
			
		}else if(iotFilter.missingData()) 
			throw new MissingDataException("Missing data of requested IOT Thing!");
		
		else {
			responseList = iotDBservice.getAllIOTThings().stream()
						.filter(iot -> (iot.getModel().equals(iotFilter.getModel())
								&& iot.getManufacturer().equals(iotFilter.getManufacturer())
								&& iot.getType().equals(iotFilter.getType())))
						.collect(Collectors.toList());
			status = Status.CREATED;
		}
		
		if(responseList.isEmpty())
			throw new MissingDataException("We could not find a match for you - try other inputs!");
	
		return Response.status(status).entity(responseList).build();
	}

}


