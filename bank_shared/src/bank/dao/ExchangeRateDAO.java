package bank.dao;

import java.rmi.Remote;
import java.rmi.RemoteException;

import bank.model.ExchangeRate;

public interface ExchangeRateDAO extends Remote {
	ExchangeRate getExchangeRate(String fromCurrency, String toCurrency) throws RemoteException;
}
