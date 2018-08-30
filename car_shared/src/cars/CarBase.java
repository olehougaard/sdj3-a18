package cars;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CarBase extends Remote {
	Car registerCar(String model, int year, Money price);
	List<Car> getAllCars() throws RemoteException;
	void removeCar(Car car) throws RemoteException;
}
