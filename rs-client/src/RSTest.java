import java.util.ArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;

import dk.via.user.User;

public class RSTest {
	public static void main(String[] args) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		
		WebTarget target = client.target("http://localhost:8080/rs-json");
		User user = target.path("users").path("0").request().accept(MediaType.APPLICATION_JSON).get(User.class);
		System.out.println(user);
		ArrayList<User> users = target.path("users").request().accept(MediaType.APPLICATION_JSON).get(new GenericType<ArrayList<User>>() {});
		System.out.println(users);
		User userData = new User(-1, "user@users.com", "adlkfj");
		User createdUser = target.path("users").request(MediaType.APPLICATION_JSON).post(Entity.json(userData)).readEntity(User.class);
		System.out.println(createdUser);
	}
}
