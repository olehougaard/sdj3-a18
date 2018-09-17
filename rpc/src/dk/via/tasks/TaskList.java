package dk.via.tasks;

import java.util.ArrayList;

import dk.via.tasks.rpc.TaskListInterface;

public class TaskList implements TaskListInterface {
	private ArrayList<Task> tasks;
	
	public TaskList() {
		tasks = new ArrayList<>();
	}
	
	/* (non-Javadoc)
	 * @see dk.via.tasks.TaskListInterface#add(dk.via.tasks.Task)
	 */
	@Override
	public void add(Task task) {
		tasks.add(task);
	}
	
	/* (non-Javadoc)
	 * @see dk.via.tasks.TaskListInterface#getAndRemoveNextTask()
	 */
	@Override
	public Task getAndRemoveNextTask() {
		return tasks.remove(0);
	}
	
	/* (non-Javadoc)
	 * @see dk.via.tasks.TaskListInterface#size()
	 */
	@Override
	public int size() {
		return tasks.size();
	}
}
