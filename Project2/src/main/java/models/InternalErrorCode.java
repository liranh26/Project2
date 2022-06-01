package models;

public enum InternalErrorCode {
	
	INVALID_INPUT(404); // TODO ---> add codes
	
	private int codeNum;
	
	private InternalErrorCode(int codeNum) {
		this.codeNum = codeNum;
	}
	public int getCodeNum() {
		return codeNum;
	}
}
