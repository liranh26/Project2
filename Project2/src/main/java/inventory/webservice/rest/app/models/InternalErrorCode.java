package inventory.webservice.rest.app.models;

public enum InternalErrorCode {
	
	NOT_FOUND(404),INTERNAL_SERVER_ERROR(500); // TODO ---> add codes
	
	private int codeNum;
	
	private InternalErrorCode(int codeNum) {
		this.codeNum = codeNum;
	}
	public int getCodeNum() {
		return codeNum;
	}
}
