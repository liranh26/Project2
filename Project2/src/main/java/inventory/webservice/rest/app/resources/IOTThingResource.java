package inventory.webservice.rest.app.resources;

import java.util.ArrayList;
import java.util.List;

import inventory.webservice.rest.app.beans.IOTThingFilterBean;
import inventory.webservice.rest.app.dbService.IOTThingDBService;
import inventory.webservice.rest.app.exceptions.MissingDataException;
import inventory.webservice.rest.app.models.IOTThing;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("inventory")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IOTThingResource {

	IOTThingDBService iotDBservice = new IOTThingDBService();

	@GET
	public Response getAllIOTThings(@BeanParam IOTThingFilterBean iotFilter) throws MissingDataException {

		Status status = Status.NOT_FOUND;

		List<IOTThing> responseList = new ArrayList<IOTThing>();

		if (iotFilter.isEmpty()) {
			status = Status.ACCEPTED;
			responseList = iotDBservice.getAllIOTThings();

		} else {
			responseList = iotDBservice.getThingsByProperties(iotFilter);
			status = Status.CREATED;
		}

		return Response.status(status).entity(responseList).build();
	}

	@GET
	@Path("/{id}")
	public Response getIOTThingById(@PathParam("id") String id) {
		return Response.status(Status.OK).entity(iotDBservice.getIOTThingById(id)).build();
	}

}
