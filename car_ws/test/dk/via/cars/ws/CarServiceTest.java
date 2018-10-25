package dk.via.cars.ws;

import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import dk.via.cars.CarDTO;
import dk.via.cars.ws.CarService;

public class CarServiceTest {

	private static CarService carService;
	private static CarDTO car;

	@BeforeClass
	public static void setUp() throws Exception {
		URL wsdl = new URL("http://localhost:8080/ws/car?wsdl");
		QName qname = new QName("http://ws.cars.via.dk/", "CarServiceImplService");
		Service service = Service.create(wsdl, qname);
		carService = service.getPort(CarService.class);
	}

	@AfterClass
	public static void tearDown() throws Exception {
		carService.delete("CD 12 345");
	}

	@Test
	public void testLicenseNumber() {
		Assert.assertEquals("CD 12 345", car.getLicenseNumber());
	}

	@Test
	public void testReadAll() throws RemoteException {
		CarDTO[] all = carService.readAll();
		boolean found = false;
		for(CarDTO car: all) {
			if (car.getLicenseNumber().equals("CD 12 345")) {
				found = true;
				break;
			}
		}
		Assert.assertTrue(found);
	}
}
