package bank.dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import bank.model.Account;
import bank.model.AccountNumber;
import bank.model.Money;
import bank.model.transaction.DepositTransaction;
import bank.model.transaction.Transaction;
import bank.model.transaction.TransactionVisitor;
import bank.model.transaction.TransferTransaction;
import bank.model.transaction.WithdrawTransaction;

public class TransactionDAOService extends UnicastRemoteObject implements TransactionDAO {
	private static final String DEPOSIT = "Deposit";
	private static final String TRANSFER = "Transfer";
	private static final String WITHDRAWAL = "Withdrawal";
	
	private static final long serialVersionUID = 1L;
	private DatabaseHelper<Transaction> helper;
	
	public TransactionDAOService(String jdbcURL, String username, String password) throws RemoteException {
		helper = new DatabaseHelper<>(jdbcURL, username, password);
	}
	
	private static class TransactionMapper implements DataMapper<Transaction> {
		@Override
		public Transaction create(ResultSet rs) throws SQLException {
			Money amount = new Money(rs.getBigDecimal("amount"), rs.getString("currency"));
			String text = rs.getString("transaction_text");
			AccountNumber primaryAccount = new AccountNumber(rs.getInt("primary_reg_number"), rs.getLong("primary_account_number"));
			switch(rs.getString("transaction_type")) {
			case DEPOSIT:
				return new DepositTransaction(amount, primaryAccount, text);
			case WITHDRAWAL:
				return new WithdrawTransaction(amount, primaryAccount, text);
			case TRANSFER:
				AccountNumber secondaryAccount = new AccountNumber(rs.getInt("secondary_reg_number"), rs.getLong("secondary_account_number"));
				return new TransferTransaction(amount, primaryAccount, secondaryAccount, text);
			default:
				return null;
			}
		}
	}
	
	private class TransactionCreator implements TransactionVisitor {
		@Override
		public void visit(DepositTransaction transaction) throws RemoteException {
			Money amount = transaction.getAmount();
			AccountNumber primaryAccount = transaction.getAccountNumber();
			helper.executeUpdate("INSERT INTO Transaction(amount, currency, transaction_type, transaction_text, primary_reg_number, primary_account_number) VALUES (?, ?, ?, ?, ?)", 
					amount.getAmount(), amount.getCurrency(), DEPOSIT, transaction.getText(), primaryAccount.getRegNumber(), primaryAccount.getAccountNumber());
		}

		@Override
		public void visit(WithdrawTransaction transaction) throws RemoteException {
			Money amount = transaction.getAmount();
			AccountNumber primaryAccount = transaction.getAccountNumber();
			helper.executeUpdate("INSERT INTO Transaction(amount, currency, transaction_type, transaction_text, primary_reg_number, primary_account_number) VALUES (?, ?, ?, ?, ?)", 
					amount.getAmount(), amount.getCurrency(), WITHDRAWAL, transaction.getText(), primaryAccount.getRegNumber(), primaryAccount.getAccountNumber());
		}

		@Override
		public void visit(TransferTransaction transaction) throws RemoteException {
			Money amount = transaction.getAmount();
			AccountNumber primaryAccount = transaction.getAccountNumber();
			AccountNumber secondaryAccount = transaction.getRecipientNumber();
			helper.executeUpdate("INSERT INTO Transaction(amount, currency, transaction_type, transaction_text, primary_reg_number, primary_account_number, secondary_reg_number, secondary_account_number) VALUES (?, ?, ?, ?, ?, ?, ?)", 
					amount.getAmount(), amount.getCurrency(), TRANSFER, transaction.getText(), primaryAccount.getRegNumber(), primaryAccount.getAccountNumber(), secondaryAccount.getRegNumber(), secondaryAccount.getAccountNumber());
		}
	}
	
	private final TransactionCreator creator = new TransactionCreator();
	
	@Override
	public void create(Transaction transaction) throws RemoteException {
		transaction.accept(creator);
	}

	@Override
	public Transaction read(int transactionId) throws RemoteException {
		return helper.mapSingle(new TransactionMapper(), "SELECT * FROM Transaction WHERE transaction_id = ?", transactionId);
	}

	@Override
	public List<Transaction> readAllFor(AccountNumber accountNumber) throws RemoteException {
		return helper.map(new TransactionMapper(), 
				"SELECT * FROM Transaction WHERE (primary_reg_number = ? AND primary_account_number = ?) OR (secondary_reg_number = ? AND secondary_account_number = ?)",
				accountNumber.getRegNumber(), accountNumber.getAccountNumber(),accountNumber.getRegNumber(), accountNumber.getAccountNumber());
	}

	@Override
	public void deleteFor(Account account) throws RemoteException {
		AccountNumber accountNumber = account.getAccountNumber();
		helper.executeUpdate("DELETE FROM Transaction WHERE (primary_reg_number = ? AND primary_account_number = ?) OR (secondary_reg_number = ? AND secondary_account_number = ?)",
				accountNumber.getRegNumber(), accountNumber.getAccountNumber(),accountNumber.getRegNumber(), accountNumber.getAccountNumber());
		
	}
}
