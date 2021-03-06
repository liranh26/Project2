package inventory.webservice.rest.app.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
/*
 * GeneralExceptionMapper catches general exceptions via the ExceptionMapper.
 * returns an INTERNAL_SERVER_ERROR = 500.
 */
@Provider
public class GeneralExceptionMapper implements ExceptionMapper<Throwable>{

	@Override
	public Response toResponse(Throwable exception) {
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage()).build();
	}

}
