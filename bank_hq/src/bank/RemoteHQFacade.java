package bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

import bank.dao.AccountDAO;
import bank.dao.AccountDAOService;
import bank.dao.CustomerDAO;
import bank.dao.CustomerDAOService;
import bank.dao.ExchangeRateDAO;
import bank.dao.ExchangeRateDAOService;
import bank.dao.HeadQuarters;
import bank.dao.TransactionDAO;
import bank.dao.TransactionDAOService;
import bank.model.Account;
import bank.model.AccountNumber;
import bank.model.Customer;
import bank.model.Money;

public class RemoteHQFacade extends UnicastRemoteObject implements HQFacade{
	private static final long serialVersionUID = 1L;
	private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=bank";
	private static final String USERNAME = "postgres";
	private static final String PASSWORD = "password";
	
	private ExchangeRateDAO exchangeDao;
	private AccountDAOService accountDAO;
	private CustomerDAOService customerDAO;
	private TransactionDAO transactionDAOService;

	public RemoteHQFacade() throws RemoteException {
		exchangeDao = new ExchangeRateDAOService(JDBC_URL, USERNAME, PASSWORD);
		accountDAO = new AccountDAOService(JDBC_URL, USERNAME, PASSWORD);
		transactionDAOService = new TransactionDAOService(accountDAO, JDBC_URL, USERNAME, PASSWORD);
		customerDAO = new CustomerDAOService(JDBC_URL, USERNAME, PASSWORD, accountDAO);
	}

	@Override
	public Customer createCustomer(String cpr, String name, String address) throws RemoteException {
		return customerDAO.create(cpr, name, address);
	}

	@Override
	public Customer getCustomer(String cpr) throws RemoteException {
		return customerDAO.read(cpr);
	}

	@Override
	public Account createAccount(Customer customer, String currency, int registrationNumber) throws RemoteException {
		return accountDAO.create(registrationNumber, customer, currency);
	}

	@Override
	public Account getAccount(AccountNumber accountNumber) throws RemoteException {
		return accountDAO.read(accountNumber);
	}

	@Override
	public void cancelAccount(Account account) throws RemoteException {
		accountDAO.delete(account);
	}

	@Override
	public Collection<Account> getAccounts(Customer customer) throws RemoteException {
		return accountDAO.readAccountsFor(customer);
	}

	@Override
	public void deposit(Account account, Money amount) throws RemoteException {
		account.deposit(amount);
		accountDAO.update(account);
	}

	@Override
	public void withdraw(Account account, Money amount) throws RemoteException {
		account.withdraw(amount);
		accountDAO.update(account);
	}
}
