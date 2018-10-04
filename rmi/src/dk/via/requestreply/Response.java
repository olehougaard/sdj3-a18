package dk.via.requestreply;

import java.io.Serializable;
import java.rmi.RemoteException;

public abstract class Response implements Serializable {
	private static final long serialVersionUID = 1L;

	public abstract Serializable getValue() throws RemoteException; 
}
