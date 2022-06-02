package inventory.webservice.rest.app.exceptions;

import inventory.webservice.rest.app.models.ErrorMessage;
import inventory.webservice.rest.app.models.InternalErrorCode;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
@Provider
public class MissingDataExceptionMapper implements ExceptionMapper<MissingDataException>{

	@Override
	public Response toResponse(MissingDataException e) {
		ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), InternalErrorCode.NOT_FOUND, "google.com");
		return Response.status(Status.NOT_FOUND)
		.entity(errorMessage)
		.build();
	}
}
