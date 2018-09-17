package bank;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

import bank.model.Account;
import bank.model.AccountNumber;
import bank.model.Customer;
import bank.model.transaction.Transaction;

public interface Branch extends Remote {
	Customer createCustomer(String cpr, String name, String address) throws RemoteException;
	Customer getCustomer(String cpr) throws RemoteException;
	Account createAccount(Customer customer, String currency) throws RemoteException;
	Account getAccount(AccountNumber accountNumber) throws RemoteException;
	void cancelAccount(AccountNumber accountNumber) throws RemoteException;
	Collection<Account> getAccounts(Customer customer) throws RemoteException;
	void execute(Transaction t) throws RemoteException;
}
