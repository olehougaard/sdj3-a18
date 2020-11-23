package bank;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

import bank.model.Account;
import bank.model.AccountNumber;
import bank.model.Customer;
import bank.model.Money;

public interface HQFacade extends Remote {
	Customer createCustomer(String cpr, String name, String address) throws RemoteException;
	Customer getCustomer(String cpr) throws RemoteException;
	Account createAccount(Customer customer, String currency, int registrationNumber) throws RemoteException;
	Account getAccount(AccountNumber accountNumber) throws RemoteException;
	void cancelAccount(Account account) throws RemoteException;
	Collection<Account> getAccounts(Customer customer) throws RemoteException;
	void deposit(Account account, Money amount) throws RemoteException;
	void withdraw(Account account, Money amount) throws RemoteException;
}
