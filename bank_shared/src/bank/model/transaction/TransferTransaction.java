package bank.model.transaction;

import java.rmi.RemoteException;

import bank.model.Account;
import bank.model.Money;

public class TransferTransaction implements Transaction {
	private static final long serialVersionUID = 1L;
	private WithdrawTransaction withdrawTransaction;
	private DepositTransaction depositTransaction;
	
	public TransferTransaction(Money amount, Account account, Account recipient) {
		this.withdrawTransaction = new WithdrawTransaction(amount, account, "Transfered " + amount + " to " + recipient);
		this.depositTransaction = new DepositTransaction(amount, recipient, "Transfered" + amount + "from " + recipient);
	}
	
	public TransferTransaction(Money amount, Account account, Account recipient, String text) {
		this.withdrawTransaction = new WithdrawTransaction(amount, account, text);
		this.depositTransaction = new DepositTransaction(amount, recipient, text);
	}

	public Money getAmount() {
		return withdrawTransaction.getAmount();
	}

	public Account getAccount() {
		return withdrawTransaction.getAccount();
	}
	
	public Account getRecipient() {
		return depositTransaction.getAccount();
	}
	
	public WithdrawTransaction getWithdrawTransaction() {
		return withdrawTransaction;
	}

	public DepositTransaction getDepositTransaction() {
		return depositTransaction;
	}

	@Override
	public void accept(TransactionVisitor visitor) throws RemoteException {
		visitor.visit(this);
	}

	@Override
	public String getText() {
		return withdrawTransaction.getText();
	}
}
