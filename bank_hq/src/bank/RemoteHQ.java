package bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import bank.dao.AccountDAO;
import bank.dao.AccountDAOService;
import bank.dao.CustomerDAO;
import bank.dao.CustomerDAOService;
import bank.dao.ExchangeRateDAO;
import bank.dao.ExchangeRateDAOService;
import bank.dao.HeadQuarters;
import bank.dao.TransactionDAO;
import bank.dao.TransactionDAOService;

public class RemoteHQ extends UnicastRemoteObject implements HeadQuarters {
	private static final long serialVersionUID = 1L;
	private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=bank";
	private static final String USERNAME = "postgres";
	private static final String PASSWORD = "password";
	
	private ExchangeRateDAO exchangeDao;
	private AccountDAOService accountDAO;
	private CustomerDAOService customerDAO;
	private TransactionDAO transactionDAOService;

	public RemoteHQ() throws RemoteException {
		exchangeDao = new ExchangeRateDAOService(JDBC_URL, USERNAME, PASSWORD);
		accountDAO = new AccountDAOService(JDBC_URL, USERNAME, PASSWORD);
		transactionDAOService = new TransactionDAOService(accountDAO, JDBC_URL, USERNAME, PASSWORD);
		customerDAO = new CustomerDAOService(JDBC_URL, USERNAME, PASSWORD, accountDAO);
	}

	@Override
	public ExchangeRateDAO getExchangeDAO() throws RemoteException {
		return exchangeDao;
	}

	@Override
	public AccountDAO getAccountDAO() throws RemoteException {
		return accountDAO;
	}

	@Override
	public CustomerDAO getCustomerDAO() throws RemoteException {
		return customerDAO;
	}

	@Override
	public TransactionDAO getTransactionDAO() throws RemoteException {
		return transactionDAOService;
	}
}
