package dk.via.tasks;

import java.util.ArrayList;

import dk.via.tasks.rmi.TaskListInterface;

public class TaskList implements TaskListInterface {
	private ArrayList<Task> tasks;
	
	public TaskList() {
		tasks = new ArrayList<>();
	}
	
	@Override
	public void add(Task task) {
		tasks.add(task);
	}
	
	@Override
	public Task getAndRemoveNextTask() {
		return tasks.remove(0);
	}
	
	@Override
	public int size() {
		return tasks.size();
	}
}
