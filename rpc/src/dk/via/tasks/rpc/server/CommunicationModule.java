package dk.via.tasks.rpc.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

import dk.via.tasks.TaskList;
import dk.via.tasks.rpc.Message;

public class CommunicationModule {
	public static final int PORT = 9090;
	
	public static void main(String[] args) throws Exception {
		TaskList list = new TaskList();
		listen(list);
	}

	private static void listen(TaskList list) throws IOException {
		@SuppressWarnings("resource") // Main socket is open until server shuts down.
		ServerSocket socket = new ServerSocket(PORT);
		Skeleton skeleton = new Skeleton(list);
		while (true) {
			try(Socket accept = socket.accept();
					ObjectInputStream ois = new ObjectInputStream(accept.getInputStream());
					ObjectOutputStream oos = new ObjectOutputStream(accept.getOutputStream())) {
				Object objectIn = ois.readObject();
				if (objectIn instanceof Message) {
					try {
						Serializable result = skeleton.interpret((Message) objectIn);
						oos.writeObject(result);
					} catch (Throwable e) {
						oos.writeObject(e);
					}
				} else {
					oos.writeObject(new IllegalArgumentException("Illegal class: " + objectIn.getClass()));
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
