package bank;

import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import bank.dao.AccountDAO;
import bank.dao.AccountDAOService;
import bank.dao.CustomerDAO;
import bank.dao.CustomerDAOService;
import bank.dao.DataProvider;
import bank.dao.ExchangeRateDAO;
import bank.dao.ExchangeRateDAOService;
import bank.dao.HelperProvider;
import bank.dao.TransactionDAO;
import bank.dao.TransactionDAOService;

public class RunHQ {
	private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=bank";
	private static final String USERNAME = "postgres";
	private static final String PASSWORD = "password";

	public static void main(String[] args) throws AccessException, RemoteException {
		DataProvider provider = new HelperProvider(JDBC_URL, USERNAME, PASSWORD);
		ExchangeRateDAO exchangeDao = new ExchangeRateDAOService(provider);
		AccountDAO accountDAO = new AccountDAOService(provider);
		TransactionDAO transactionDAOService = new TransactionDAOService(accountDAO, provider);
		CustomerDAO customerDAO = new CustomerDAOService(accountDAO, provider);
		RemoteHQ hq = new RemoteHQ(exchangeDao, accountDAO, transactionDAOService, customerDAO);
		
		Registry registry = LocateRegistry.createRegistry(1099);
		registry.rebind("HQ", hq);
	}
}
