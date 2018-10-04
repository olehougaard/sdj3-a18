package dk.via.requestreply.server;

import dk.via.requestreply.Message;
import dk.via.requestreply.Response;

public interface Recipient {

	Response interpret(Message message);

}