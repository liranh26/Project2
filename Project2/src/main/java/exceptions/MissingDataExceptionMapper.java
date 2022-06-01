package exceptions;


import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import models.ErrorMessage;
import models.InternalErrorCode;
@Provider
public class MissingDataExceptionMapper implements ExceptionMapper<MissingDataException>{

	@Override
	public Response toResponse(MissingDataException e) {
		ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), InternalErrorCode.INVALID_INPUT, "google.com");
		return Response.status(Status.NOT_FOUND)
		.entity(errorMessage)
		.build();
	}
}



//	@Override
//	public Response toResponse(MissingDataException e) {
//		ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), InternalErrorCode.INVALID_INPUT, "google.com");
//		return Response.status(Status.NOT_FOUND).entity(errorMessage).build();
//	}
//






//	public static class Error {
//        public String key;
//        public String message;
//    }
//
//    @Override
//    public Response toResponse(MissingDataException exception) {
//        Error error = new Error();
//        error.key = "bad-json";
//        error.message = exception.getMessage();
//        return Response.status(400).entity(error).build();
//    }
//
//	
//	
//	@Override
//	public Response toResponse(MissingDataException e) {
//	
//		return Response.status(346)
//				.entity("Unfortunately, the application cannot process your request at this time.")
//				.type("text/plain").build();
//	}