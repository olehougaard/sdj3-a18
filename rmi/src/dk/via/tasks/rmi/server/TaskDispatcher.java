package dk.via.tasks.rmi.server;

import java.io.Serializable;

import dk.via.requestreply.Message;
import dk.via.requestreply.server.Recepient;
import dk.via.tasks.Task;
import dk.via.tasks.TaskList;

public class TaskDispatcher implements Recepient {
	private TaskList tasks;
	
	public TaskDispatcher(TaskList tasks) {
		this.tasks = tasks;
	}
	
	@SafeVarargs
	private final void checkArgs(Serializable[] args, Class<? extends Serializable>... expected) {
		if (args == null || args.length != expected.length) throw new IllegalArgumentException("Wrong number of arguments: " + args.length);
		for(int i = 0; i < args.length; i++) {
			if (!expected[i].isInstance(args[i])) throw new IllegalArgumentException("Incompatible argument type");
		}
	}

	@Override
	public Serializable interpret(Message message) {
		final String method = message.getMethod();
		final Serializable[] args = message.getArgs();
		switch(method) {
		case "add":
			checkArgs(args, Task.class);
			tasks.add((Task) args[0]);
			return Message.VOID;
		case "getAndRemoveNextTask":
			checkArgs(args);
			return tasks.getAndRemoveNextTask();
		case "size":
			checkArgs(args);
			return Integer.valueOf(tasks.size());
		default:
			throw new IllegalArgumentException("Invalid method");	
		}
	}
}
