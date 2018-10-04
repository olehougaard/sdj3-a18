package dk.via.requestreply;

import java.io.Serializable;
import java.rmi.RemoteException;

public class ValueResponse extends Response {
	private static final long serialVersionUID = 1L;

	private Serializable value;
	
	public ValueResponse(Serializable value) {
		this.value = value;
	}

	@Override
	public Serializable getValue() throws RemoteException {
		return value;
	}
}
