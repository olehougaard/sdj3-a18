package dk.via.requestreply;

import java.io.Serializable;
import java.rmi.RemoteException;

public class ExceptionResponse extends Response {
	private static final long serialVersionUID = 1L;

	private Exception exception;
	
	public ExceptionResponse(Exception exception) {
		this.exception = exception;
	}

	@Override
	public Serializable getValue() throws RemoteException {
		throw new RemoteException(exception.getMessage(), exception);
	}
}
