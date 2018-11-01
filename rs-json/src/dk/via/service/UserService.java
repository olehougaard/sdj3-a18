package dk.via.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dk.via.user.Email;
import dk.via.user.User;

@Path("/users")
public class UserService {
	private UserDAO dao = UserDAO.getInstance();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<User> readUsers() {
		return dao.readUsers();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readUser(@PathParam("id") String id) throws IOException {
		try {
			User user = dao.readUser(Integer.parseInt(id));
			return Response.status(200).entity(user).build();
		} catch (NumberFormatException e) {
			return Response.status(400).build();
		} catch (IndexOutOfBoundsException e) {
			return Response.status(404).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(User user) {
		User createdUser = dao.createUser(user.getEmail(), user.getPassword());
		return Response.status(201).entity(createdUser).build();
	}

	@GET
	@Path("{id}/outbox")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOutbox(@PathParam("id") String id) throws IOException {
		try {
			User user = dao.readUser(Integer.parseInt(id));
			return Response.status(200).entity(dao.getEmailsFrom(user)).build();
		} catch (NumberFormatException e) {
			return Response.status(400).build();
		} catch (IndexOutOfBoundsException e) {
			return Response.status(404).build();
		}
	}

	@POST
	@Path("{id}/outbox")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response send(@PathParam("id") String id, Email email) throws IOException {
		try {
			User user = dao.readUser(Integer.parseInt(id));
			return Response.status(201).entity(dao.createEmail(user, email.getRecipient(), email.getMessage())).build();
		} catch (NumberFormatException e) {
			return Response.status(400).build();
		} catch (IndexOutOfBoundsException e) {
			return Response.status(404).build();
		}
	}

	@GET
	@Path("{id}/inbox")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInbox(@PathParam("id") String id) throws IOException {
		try {
			User user = dao.readUser(Integer.parseInt(id));
			return Response.status(200).entity(dao.getEmailsTo(user)).build();
		} catch (NumberFormatException e) {
			return Response.status(400).build();
		} catch (IndexOutOfBoundsException e) {
			return Response.status(404).build();
		}
	}
}
