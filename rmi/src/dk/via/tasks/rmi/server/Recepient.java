package dk.via.tasks.rmi.server;

import java.io.Serializable;

import dk.via.tasks.rmi.Message;

public interface Recepient {

	Serializable interpret(Message message);

}