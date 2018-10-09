package bank;

import com.google.inject.AbstractModule;

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
	@Override
	protected void configure() {
		bind(String.class).annotatedWith(JdbcUrl.class).toInstance("jdbc:postgresql://localhost:5432/postgres?currentSchema=bank");
		bind(String.class).annotatedWith(Username.class).toInstance("postgres");
		bind(String.class).annotatedWith(Password.class).toInstance("password");
		
		bind(DataProvider.class).to(HelperProvider.class);
		bind(ExchangeRateDAO.class).to(ExchangeRateDAOService.class);
		bind(AccountDAO.class).to(AccountDAOService.class);
		bind(TransactionDAO.class).to(TransactionDAOService.class);
		bind(CustomerDAO.class).to(CustomerDAOService.class);
		bind(HeadQuarters.class).to(RemoteHQ.class);
	}
}
