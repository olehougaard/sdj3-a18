package dk.via.requestreply.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;

import dk.via.requestreply.Message;

public class CommunicationModule {
	public static final int PORT = 9090;
	
	public static Serializable doOperation(Message msg) throws RemoteException {
		try (Socket socket = new Socket("localhost", PORT);
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
			oos.writeObject(msg);
			try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
				final Object reply = ois.readObject();
				return (Serializable) reply;
			}
		} catch (Exception e) {
			throw new RemoteException(e.getMessage(), e);
		}
	}
}
