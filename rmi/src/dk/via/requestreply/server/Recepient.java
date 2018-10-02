package dk.via.requestreply.server;

import java.io.Serializable;

import dk.via.requestreply.Message;

public interface Recepient {

	Serializable interpret(Message message);

}