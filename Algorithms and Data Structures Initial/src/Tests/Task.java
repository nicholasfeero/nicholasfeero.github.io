/*
 * Nicholas Feero
 * CS-320: Software Test & Automation
 * Module #4 Milestone | Task.java
 * Professor Toledo
 * February 2nd, 2025
*/

//creation of Task class
package Tests;

public class Task {
	private String taskId;
	private String taskName;
	private String taskDescription;
	
	//criteria for creating an object of the class
	public Task(String taskId, String taskName, String taskDescription) {
		//check if task ID meets character limit
		if (taskId == null || taskId.length() > 10) {
			throw new IllegalArgumentException("Invalid Task ID");
		}
		//check if task name meets character limit
		if (taskName == null || taskName.length() > 20) {
			throw new IllegalArgumentException("Invalid Task Name");
		}
		//check if task description meets character limit
		if (taskDescription == null || taskDescription.length() > 50) {
			throw new IllegalArgumentException("Invalid Task Description");
		}
		//put in place to handle variables assuming there are no exceptions
		this.taskId = taskId;
		this.taskName = taskName;
		this.taskDescription = taskDescription;
	}
	
	//getter methods for each variable
	public String getTaskId() {
		return taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	
	//setter methods for taskName and taskDescription respectively to be used in TaskService
	//there does not exist a setter method for taskId as this variable shouldn't be altered or updated
	public void setTaskName(String newTaskName) {
		if (newTaskName == null || newTaskName.length() > 20) {
			throw new IllegalArgumentException("Invalid Task Name");
		}
		this.taskName = newTaskName;
	}
	public void setTaskDescription(String newTaskDescription) {
		if (newTaskDescription == null || newTaskDescription.length() > 50) {
			throw new IllegalArgumentException("Invalid Task Description");
		}
		this.taskDescription = newTaskDescription;
	}
}
