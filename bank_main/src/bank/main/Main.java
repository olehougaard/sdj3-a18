package bank.main;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import bank.RemoteBranch;
import bank.RemoteHQ;
import bank.dao.HeadQuarters;

public class Main {
	@SuppressWarnings("unchecked")
	private static<T extends Remote> T bind(Registry registry, String URI, T remoteObject) throws RemoteException {
		registry.rebind(URI, remoteObject);
		try {
			return (T) registry.lookup(URI);
		} catch (NotBoundException e) {
			throw new RemoteException(e.getMessage(), e);
		}
	}
	
	public static void main(String[] args) throws Exception {
		Registry registry = LocateRegistry.createRegistry(1099);
		HeadQuarters hq = bind(registry, "HQ", new RemoteHQ());
		RemoteBranch branch1 = new RemoteBranch(1234, hq);
		registry.rebind("Branch 1", branch1);
		RemoteBranch branch2 = new RemoteBranch(7255, hq);
		registry.rebind("Branch 2", branch2);
	}
}
