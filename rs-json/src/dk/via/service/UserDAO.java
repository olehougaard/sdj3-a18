package dk.via.service;

import java.util.ArrayList;
import java.util.Date;

import dk.via.user.Email;
import dk.via.user.User;

public class UserDAO {
	private static UserDAO instance = new UserDAO();

	private ArrayList<User> users;
	private ArrayList<Email> emails;

	private UserDAO() {
		users = new ArrayList<>();
		users.add(new User(0, "abc@example.com", "1234abcd"));
		users.add(new User(1, "def@hotmail.com", "password"));
		
		emails = new ArrayList<>();
		emails.add(new Email(users.get(0), users.get(1), new Date(), "Hello"));
	}
	
	public static UserDAO getInstance() {
		return instance;
	}
	
	public User createUser(String email, String password) {
		User user = new User(users.size(), email, password);
		users.add(user);
		return user;
	}

	public ArrayList<User> readUsers() {
		return users;
	}
	
	public User readUser(int id) {
		return users.get(id);
	}
	
	public Email createEmail(User sender, User recipient, String message) {
		Email email = new Email(sender, recipient, new Date(), message);
		emails.add(email);
		return email;
	}
	
	public ArrayList<Email> getEmailsFrom(User user) {
		ArrayList<Email> result = new ArrayList<>();
		for(Email email: emails)
			if (email.getSender().equals(user))
				result.add(email);
		return result;
	}
	
	public ArrayList<Email> getEmailsTo(User user) {
		ArrayList<Email> result = new ArrayList<>();
		for(Email email: emails)
			if (email.getRecipient().equals(user))
				result.add(email);
		return result;
	}
}
