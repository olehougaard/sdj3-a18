package dk.via.tasks.rmi.server;

import java.io.IOException;
import java.io.Serializable;

import dk.via.requestreply.Message;
import dk.via.requestreply.server.Recipient;
import dk.via.tasks.Task;
import dk.via.tasks.TaskList;
import dk.via.util.ByteConverter;

public class TaskDispatcher implements Recipient {
	private TaskList tasks;
	
	public TaskDispatcher(TaskList tasks) {
		this.tasks = tasks;
	}
	
	@SafeVarargs
	private final Serializable[] unmarshal(byte[][] args, Class<? extends Serializable>... expected) {
		if (args == null || args.length != expected.length) throw new IllegalArgumentException("Wrong number of arguments: " + args.length);
		Serializable[] sar = new Serializable[args.length];
		for(int i = 0; i < args.length; i++) {
			try {
				sar[i] = ByteConverter.serializableFromByteArray(args[i]);
			} catch (ClassNotFoundException | IOException e) {
				throw new IllegalArgumentException(e);
			}
			if (!expected[i].isInstance(sar[i])) throw new IllegalArgumentException("Incompatible argument type");
		}
		return sar;
	}
	
	@Override
	public byte[] interpret(Message message) {
		final String method = message.getMethod();
		final byte[][] args = message.getArgs();
		switch(method) {
		case "add": {
			Serializable[] unmarshalled = unmarshal(args, Task.class);
			tasks.add((Task) unmarshalled[0]);
			return new byte[0];
		}
		case "getAndRemoveNextTask": {
			unmarshal(args);
			return ByteConverter.toByteArray(tasks.getAndRemoveNextTask());
		}
		case "size": {
			unmarshal(args);
			return ByteConverter.toByteArray(tasks.size());
		}
		default:
			return ByteConverter.toByteArray(new IllegalArgumentException("Invalid method"));	
		}
	}
}
