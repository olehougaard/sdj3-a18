package dk.via.tasks.rpc;

import java.rmi.RemoteException;

import dk.via.tasks.Task;

public interface TaskListInterface {

	void add(Task task) throws RemoteException;

	Task getAndRemoveNextTask() throws RemoteException;

	int size() throws RemoteException;

}