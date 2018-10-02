package dk.via.tasks.rmi.client;

import java.io.Serializable;
import java.rmi.RemoteException;

import dk.via.requestreply.Message;
import dk.via.requestreply.client.CommunicationModule;
import dk.via.tasks.Task;
import dk.via.tasks.rmi.TaskListInterface;

public class Proxy implements TaskListInterface {
	private Serializable call(String message, Serializable... args) throws RemoteException {
		Serializable reply = CommunicationModule.doOperation(new Message(message, args));
		if (reply instanceof Exception) {
			Exception e = (Exception) reply;
			throw new RemoteException(e.getMessage(), e);
		} else {
			return reply;
		}

	}
	
	private<T extends Serializable> T cast(Serializable s, Class<T> clazz) throws RemoteException {
		if (clazz.isInstance(s)) {
			return clazz.cast(s);
		} else {
			throw new RemoteException("Class cast exception: " + s.getClass());
		}
	}

	@Override
	public void add(Task task) throws RemoteException {
		call("add", task);
	}

	@Override
	public Task getAndRemoveNextTask() throws RemoteException {
		Serializable reply = call("getAndRemoveNextTask");
		return cast(reply, Task.class);
	}

	@Override
	public int size() throws RemoteException {
		Serializable reply = call("size");
		return cast(reply, Integer.class);
	}
}
