package inventory.webservice.rest.app.resources;

import java.util.ArrayList;
import java.util.List;

import inventory.webservice.rest.app.beans.DevicesFilterBean;
import inventory.webservice.rest.app.dbService.DeviceDBService;
import inventory.webservice.rest.app.exceptions.MissingDataException;
import inventory.webservice.rest.app.models.Device;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/*
 * DeviceResource handles the incoming GET requests for devices 
 * by id, properties and sends all current devices in the DB
 */

@Path("devices")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DeviceResource {

	DeviceDBService deviceDBService = new DeviceDBService();

	@GET
	public Response getDevices(@BeanParam DevicesFilterBean deviceFilter) throws MissingDataException {

		Status status = Status.NOT_FOUND;

		List<Device> responseList = new ArrayList<Device>();

		if (deviceFilter.isEmpty()) {
			status = Status.ACCEPTED;
			responseList = deviceDBService.getAllDevices();

		} else {
			responseList = deviceDBService.getDevicesByPropertiesFromThing(deviceFilter);
			status = Status.CREATED;
		}

		return Response.status(status).entity(responseList).build();
	}

	@GET
	@Path("/{id}")
	public Response getDeviceById(@PathParam("id") String id) {
		return Response.status(Status.OK).entity(deviceDBService.getDeviceById(id)).build();
	}

}
