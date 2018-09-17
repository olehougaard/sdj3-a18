package bank.model.transaction;

import java.rmi.RemoteException;

import bank.model.AccountNumber;
import bank.model.Money;

public class WithdrawTransaction extends AbstractTransaction {
	private static final long serialVersionUID = 1L;

	public WithdrawTransaction(Money amount, AccountNumber account) {
		this(amount, account, "Withdrew " + amount);
	}

	public WithdrawTransaction(Money amount, AccountNumber accountNumber, String text) {
		super(amount, accountNumber, text);
	}


	@Override
	public void accept(TransactionVisitor visitor) throws RemoteException {
		visitor.visit(this);
	}
}
