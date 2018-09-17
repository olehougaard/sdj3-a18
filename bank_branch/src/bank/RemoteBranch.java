package bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

import bank.dao.AccountDAO;
import bank.dao.CustomerDAO;
import bank.dao.ExchangeRateDAO;
import bank.dao.HeadQuarters;
import bank.dao.TransactionDAO;
import bank.model.Account;
import bank.model.AccountNumber;
import bank.model.Customer;
import bank.model.ExchangeRate;
import bank.model.Money;
import bank.model.transaction.DepositTransaction;
import bank.model.transaction.Transaction;
import bank.model.transaction.TransactionVisitor;
import bank.model.transaction.TransferTransaction;
import bank.model.transaction.WithdrawTransaction;

public class RemoteBranch extends UnicastRemoteObject implements Branch, TransactionVisitor {
	private static final long serialVersionUID = 1;
	private int regNumber;
	private long nextAccount = 1;
	private AccountDAO accountDAO;
	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;
	private ExchangeRateDAO exchangeDAO;
	
	public RemoteBranch(int regNumber, HeadQuarters hq) throws RemoteException {
		this.regNumber = regNumber;
		this.accountDAO = hq.getAccountDAO();
		this.customerDAO = hq.getCustomerDAO();
		this.transactionDAO = hq.getTransactionDAO();
		this.exchangeDAO = hq.getExchangeDAO();
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
	public Account createAccount(Customer customer, String currency) throws RemoteException {
		AccountNumber accountNumber;
		synchronized(RemoteBranch.class) {
			accountNumber = new AccountNumber(regNumber, nextAccount++);
		}
		return accountDAO.create(accountNumber, customer, currency);
	}

	@Override
	public Account getAccount(AccountNumber accountNumber) throws RemoteException {
		return accountDAO.read(accountNumber);
	}
	
	@Override
	public void cancelAccount(AccountNumber accountNumber) throws RemoteException {
		accountDAO.delete(accountDAO.read(accountNumber));
	}

	@Override
	public Collection<Account> getAccounts(Customer customer) throws RemoteException {
		return accountDAO.readAccountsFor(customer);
	}
	
	@Override
	public void execute(Transaction t) throws RemoteException {
		t.accept(this);
		transactionDAO.create(t);
	}
	
	private Money translateToSettledCurrency(Money amount, Account account) throws RemoteException {
		if (!amount.getCurrency().equals(account.getSettledCurrency())) {
			ExchangeRate rate = exchangeDAO.getExchangeRate(amount.getCurrency(), account.getSettledCurrency());
			amount = rate.exchange(amount);
		}
		return amount;
	}

	@Override
	public void visit(DepositTransaction transaction) throws RemoteException {
		Account account = accountDAO.read(transaction.getAccountNumber());
		Money amount = transaction.getAmount();
		amount = translateToSettledCurrency(amount, account);
		account.deposit(amount);
		accountDAO.update(account);
	}
	
	@Override
	public void visit(WithdrawTransaction transaction) throws RemoteException {
		Account account = accountDAO.read(transaction.getAccountNumber());
		Money amount = transaction.getAmount();
		amount = translateToSettledCurrency(amount, account);
		account.withdraw(amount);
		accountDAO.update(account);
	}
	
	@Override
	public void visit(TransferTransaction transaction) throws RemoteException {
		transaction.getDepositTransaction().accept(this);
		transaction.getWithdrawTransaction().accept(this);
	}
}
