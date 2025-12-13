/*
 * Nicholas Feero
 * CS-320: Software Test & Automation
 * Module #4 Milestone | TaskService.java
 * Professor Toledo
 * February 2nd, 2025
*/

package Tests;

import java.util.ArrayList;

//creation of TaskService class
public class TaskService {
	//create an ArrayList to store tasks
	public ArrayList<Task> taskList = new ArrayList<Task>(0);
	//display full list of tasks to check for errors
	public void displayTaskList() {
		for (int i = 0; i < taskList.size(); i++) {
			System.out.println("Task ID: " + taskList.get(i).getTaskId());
			System.out.println("Task Name: " + taskList.get(i).getTaskName());
			System.out.println("Task Description: " + taskList.get(i).getTaskDescription());
		}
	}
	//adds a new task if task list is empty
	//checks task list if there's an identical task ID. If so, refuse addition of task
	//if task list isn't empty but has a unique task ID entry, add task
	public boolean addTask(Task task) {
		boolean didAdd = false;
		//list is empty
		if (taskList.size() == 0 ) {
			taskList.add(task);
			didAdd = true;
		}
		else {
			for(Task c : taskList) {
				if (task.getTaskId().equalsIgnoreCase(c.getTaskId())) {
					return didAdd;
				}
			} //end for loop
			taskList.add(task);
			didAdd = true;
		}
		return didAdd;
	}
	//by utilizing task ID, return task object. If no task ID is found, return default value
	public Task getTask(String taskId) {
		Task task = new Task(null, null, null);
		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).getTaskId().contentEquals(taskId)) {
				task = taskList.get(i);
			}
		}
		return task;
	}
	//delete task method
	//utilize task ID to find correct task to delete
	//if list is traversed without a return of specified task ID, output no task ID found to console
	public void deleteTask(String taskId) {
		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).getTaskId().equals(taskId)) {
				taskList.remove(i);
				break;
			}
			if (i == taskList.size() - 1) {
				System.out.println("Task ID: " + taskId + " not found.");
			}
		}
	}
	//update task name utilizing task ID to find specific task
	//if list is traversed without a return of specified task ID, output no task ID found to console
	public void updateTaskName(String updatedString, String taskId) {
		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).getTaskId().equals(taskId)) {
				taskList.get(i).setTaskName(updatedString);
				break;
			}
			if (i == taskList.size() - 1) {
				System.out.println("Task ID: " + taskId + " not found.");
			}
		}
	}
	//update task description utilizing task ID to find specific task
	//if list is traversed without a return of specified task ID, output no task ID found to console
	public void updateTaskDescription(String updatedString, String taskId) {
		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).getTaskId().equals(taskId)) {
				taskList.get(i).setTaskDescription(updatedString);
				break;
			}
			if (i == taskList.size() - 1) {
				System.out.println("Task ID: " + taskId + " not found.");
			}
		}
	}	
}
