package dk.via.cars.ws;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.jws.WebService;

import dk.via.cars.CarDTO;
import dk.via.cars.DataMapper;
import dk.via.cars.DatabaseHelper;
import dk.via.cars.MoneyDTO;

@WebService(endpointInterface = "dk.via.cars.ws.CarService")
public class CarServiceImpl implements CarService {

	private dk.via.cars.DatabaseHelper<CarDTO> helper;

	public CarServiceImpl() {
		helper = new DatabaseHelper<>("jdbc:postgresql://localhost:5432/postgres?currentSchema=car_base", "postgres", "password");
	}

	@Override
	public CarDTO create(String licenseNo, String model, int year, MoneyDTO price)  {
		helper.executeUpdate("INSERT INTO car VALUES (?, ?, ?, ?, ?)", licenseNo, model, year, price.getAmount(), price.getCurrency());
		return new CarDTO(licenseNo, model, year, price);
	}
	
	private static class CarMapper implements DataMapper<CarDTO> {
		@Override
		public CarDTO create(ResultSet rs) throws SQLException {
			String licenseNumber = rs.getString("license_number");
			String model = rs.getString("model");
			int year = rs.getInt("year");
			BigDecimal priceAmount = rs.getBigDecimal("price_amount");
			String priceCurrency = rs.getString("price_currency");
			return new CarDTO(licenseNumber, model, year, priceAmount, priceCurrency);
		}
	}
	
	@Override
	public CarDTO[] readAll() {
		return helper.map(new CarMapper(), "SELECT * FROM car").toArray(new CarDTO[0]);
	}
	
	@Override
	public void delete(String license_number) {
		helper.executeUpdate("DELETE FROM car WHERE license_number = ?", license_number);
	}
}
