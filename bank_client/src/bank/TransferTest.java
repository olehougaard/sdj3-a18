package bank;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bank.dao.ExchangeRateDAO;
import bank.model.Account;
import bank.model.Customer;
import bank.model.Money;
import bank.model.transaction.DepositTransaction;
import bank.model.transaction.TransferTransaction;

public class TransferTest {
	private ExchangeRateDAO hq;
	private Branch primaryBranch;
	private Account primaryAccount;
	private Account secondaryAccount;

	@Before
	public void setUp() throws Exception {
		Registry registry = LocateRegistry.getRegistry(1099);
		primaryBranch = (Branch) registry.lookup("Branch 1");
		Customer customer = primaryBranch.getCustomer("1234567890");
		primaryAccount = primaryBranch.createAccount(customer, "DKK");
		Customer other = primaryBranch.getCustomer("1122334455");
		secondaryAccount = primaryBranch.createAccount(other, "EUR");
	}
	
	@After
	public void tearDown() throws Exception {
		primaryBranch.cancelAccount(primaryAccount.getAccountNumber());
		primaryBranch.cancelAccount(secondaryAccount.getAccountNumber());
	}
	
	@Test
	public void test() throws RemoteException {
		Money startingAmount = new Money(new BigDecimal(10000), "DKK");
		Money transferAmount = new Money(new BigDecimal(1000), "DKK");
		Money remainingAmount = new Money(new BigDecimal(9000), "DKK");
		primaryBranch.execute(new DepositTransaction(startingAmount, primaryAccount.getAccountNumber()));
		primaryAccount = primaryBranch.getAccount(primaryAccount.getAccountNumber());
		assertEquals(startingAmount, primaryAccount.getBalance());
		primaryBranch.execute(new TransferTransaction(transferAmount, primaryAccount.getAccountNumber(), secondaryAccount.getAccountNumber()));
		primaryAccount = primaryBranch.getAccount(primaryAccount.getAccountNumber());
		secondaryAccount = primaryBranch.getAccount(secondaryAccount.getAccountNumber());
		assertEquals(remainingAmount, primaryAccount.getBalance());
		assertEquals(hq.getExchangeRate("DKK", "EUR").exchange(transferAmount), secondaryAccount.getBalance());
	}
}
