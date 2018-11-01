package dk.via.cars.ws;

public class PasswordTest {
	public static void main(String[] args) {

		String userName = "Administrator";
		String passWord = "'; DROP TABLE USERS; --";
		String sql = "SELECT * FROM Users WHERE username = '" + userName + "' AND password = '" + passWord + "'";
		System.out.println(sql);
	}
}
