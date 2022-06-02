package project2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import inventory.webservice.rest.app.models.ErrorMessage;
import inventory.webservice.rest.app.models.InternalErrorCode;

class ErrorMessageTest {

	ErrorMessage errorMsg;
	String msg = "Could not find matching data in the inventory";
	String docsLink = "google.com";
	InternalErrorCode errorCode = InternalErrorCode.NOT_FOUND;
	
	ErrorMessageTest(){
		errorMsg = new ErrorMessage(msg, InternalErrorCode.NOT_FOUND, docsLink);
	}
	
	@Test
	void testErrorMsg() {
		assertTrue(errorMsg.getErrorMessage().equals(msg));
		assertTrue(errorMsg.getErrorCode().equals(errorCode));
		assertTrue(errorMsg.getErrorCode().getCodeNum() == errorCode.getCodeNum());
		assertTrue(errorMsg.getDocsLink().equals(docsLink));
	}

}
