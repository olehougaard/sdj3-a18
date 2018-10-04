package dk.via.requestreply;

import java.io.Serializable;
import java.rmi.RemoteException;

public class VoidResponse extends Response {
	private static final long serialVersionUID = 1L;

	public static final VoidResponse VOID = new VoidResponse();
	
	private VoidResponse() {
	}

	@Override
	public Serializable getValue() throws RemoteException {
		return null;
	}
}
