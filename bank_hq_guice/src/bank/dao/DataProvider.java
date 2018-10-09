package bank.dao;

import java.rmi.RemoteException;

public interface DataProvider {

	<T> DataHelper<T> createHelper(Class<T> cl) throws RemoteException;

}