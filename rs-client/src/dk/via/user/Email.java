package dk.via.user;

import java.util.Date;

public class Email {
	private User sender;
	private User recipient;
	private Date sent;
	private String message;
	
	public Email(User sender, User recipient, Date sent, String message) {
		this.sender = sender;
		this.recipient = recipient;
		this.sent = sent;
		this.message = message;
	}

	public User getSender() {
		return sender;
	}

	public User getRecipient() {
		return recipient;
	}

	public Date getSent() {
		return sent;
	}

	public String getMessage() {
		return message;
	}
	
	public Email() {}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}

	public void setSent(Date sent) {
		this.sent = sent;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return String.format("Email(%s, %s, %s, %s)", sender, recipient, sent, message);
	}
}
