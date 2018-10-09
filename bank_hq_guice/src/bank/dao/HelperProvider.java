package bank.dao;

import java.rmi.RemoteException;

public class HelperProvider implements DataProvider {
	private String jdbcURL;
	private String username;
	private String password;
	
	public HelperProvider(String jdbcURL, String username, String password) {
		this.jdbcURL = jdbcURL;
		this.username = username;
		this.password = password;
	}

	@Override
	public<T> DataHelper<T> createHelper(Class<T> cl) throws RemoteException {
		return new DatabaseHelper<T>(jdbcURL, username, password);
	}
}
