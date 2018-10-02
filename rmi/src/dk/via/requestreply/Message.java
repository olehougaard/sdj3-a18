package dk.via.requestreply;

import java.io.Serializable;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	private String method;
	private Serializable[] args;
	
	public static final Message VOID = new Message("VOID");
	
	public Message(String method, Serializable... args) {
		this.method = method;
		this.args = args;
	}

	public String getMethod() {
		return method;
	}

	public Serializable[] getArgs() {
		return args;
	}
}
