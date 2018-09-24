package bank.model.transaction;

import java.rmi.RemoteException;

import bank.model.Account;
import bank.model.Money;

public class DepositTransaction extends AbstractTransaction {
	private static final long serialVersionUID = 1L;

	public DepositTransaction(Money amount, Account account) {
		this(amount, account, "Deposited " + amount);
	}
	
	public DepositTransaction(Money amount, Account account, String text) {
		super(amount, account, text);
	}

	@Override
	public void accept(TransactionVisitor visitor) throws RemoteException {
		visitor.visit(this);
	}
}
