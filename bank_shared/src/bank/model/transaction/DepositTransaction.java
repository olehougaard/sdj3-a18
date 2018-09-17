package bank.model.transaction;

import java.rmi.RemoteException;

import bank.model.AccountNumber;
import bank.model.Money;

public class DepositTransaction extends AbstractTransaction {
	private static final long serialVersionUID = 1L;

	public DepositTransaction(Money amount, AccountNumber accountNumber) {
		this(amount, accountNumber, "Deposited " + amount);
	}
	
	public DepositTransaction(Money amount, AccountNumber accountNumber, String text) {
		super(amount, accountNumber, text);
	}

	@Override
	public void accept(TransactionVisitor visitor) throws RemoteException {
		visitor.visit(this);
	}
}
