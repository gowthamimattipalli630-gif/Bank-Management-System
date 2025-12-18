package Com.bank.exception;

public class CustomerDetailsInvalidException extends RuntimeException {
	
	private String exceptionMessage;

	public CustomerDetailsInvalidException(String exceptionMessage) {
		super();
		this.exceptionMessage = exceptionMessage;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	

}
