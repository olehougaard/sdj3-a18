package bank;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import bank.dao.AccountDAO;
import bank.dao.AccountDAOService;
import bank.dao.CustomerDAO;
import bank.dao.CustomerDAOService;
import bank.dao.DataProvider;
import bank.dao.ExchangeRateDAO;
import bank.dao.ExchangeRateDAOService;
import bank.dao.HeadQuarters;
import bank.dao.HelperProvider;
import bank.dao.TransactionDAO;
import bank.dao.TransactionDAOService;

public class BankModule extends AbstractModule {
	private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=bank";
	private static final String USERNAME = "postgres";
	private static final String PASSWORD = "password";

	@Override
	protected void configure() {
		bind(ExchangeRateDAO.class).to(ExchangeRateDAOService.class);
		bind(AccountDAO.class).to(AccountDAOService.class);
		bind(TransactionDAO.class).to(TransactionDAOService.class);
		bind(CustomerDAO.class).to(CustomerDAOService.class);
		bind(HeadQuarters.class).to(RemoteHQ.class);
	}
	
	@Provides
	DataProvider provideDataHelper() {
		return new HelperProvider(JDBC_URL, USERNAME, PASSWORD);
	}

}
