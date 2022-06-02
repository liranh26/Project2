package inventory.webservice.rest.app.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import inventory.webservice.rest.app.beans.DevicesFilterBean;
import inventory.webservice.rest.app.dbService.DeviceDBService;
import inventory.webservice.rest.app.exceptions.MissingDataException;
import inventory.webservice.rest.app.models.Device;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;


@Path("devices")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DeviceResource {
	
	DeviceDBService deviceDBService = new DeviceDBService();
	
	@GET
	public Response getAllIOTThings(@BeanParam DevicesFilterBean deviceFilter) throws MissingDataException {
		
		Status status = Status.NOT_FOUND;

		List<Device> responseList = new ArrayList<Device>();
		
		System.out.println(deviceFilter);
		
		if (deviceFilter.isEmpty()) {
			status = Status.ACCEPTED;  //TODO --> which code needed here ???
			responseList = deviceDBService.getAllDevices();
			
		}else if(deviceFilter.missingData()) 
			throw new MissingDataException("Missing data of requested IOT Thing!");
		
		else {
			responseList = deviceDBService.getAllDevices().stream()
						.filter(d -> (d.getModel().equals(deviceFilter.getModel())
								&& d.getManufacturer().equals(deviceFilter.getManufacturer())
								&& d.getType().equals(deviceFilter.getType())
								&& d.getID().equals(deviceFilter.getId())))
						.collect(Collectors.toList());
			status = Status.CREATED;
		}
		
		if(responseList.isEmpty())
			throw new MissingDataException("We could not find a match for you - try other inputs!");
		
		
		return Response.status(status).entity(responseList).build();
	}
	
	
	
}
