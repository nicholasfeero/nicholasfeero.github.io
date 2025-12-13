/*
 * Nicholas Feero
 * CS-320: Software Test & Automation
 * Module #4 Milestone | TaskServiceTest.java
 * Professor Toledo
 * February 2nd, 2025
*/

package Tests;

import static org.junit.Assert.assertTrue;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;

//creation of TaskServiceTest class
public class TaskServiceTest {
	//test creation of tasks with unique task ID using TaskService methods
	@Test
	void testTaskClass() {
		Task task = new Task("1", "Dishes", "Dirty and Clean");
		assertTrue(task.getTaskId().equals("1"));
		assertTrue(task.getTaskName().equals("Dishes"));
		assertTrue(task.getTaskDescription().equals("Dirty and Clean"));
	}
	//test to update task name by specifying task ID
	@Test
	@DisplayName("Test to update Task Name")
	void testUpdateTaskName() {
		TaskService service = new TaskService();
		Task task = new Task("11111", "Code", "Task Service File");
		service.addTask(task);
		int size = service.taskList.size();
		service.taskList.get(size - 1).getTaskId();
		service.taskList.get(size - 1).getTaskName();
		service.updateTaskName("Coding", "11111");
		service.displayTaskList();
		service.taskList.get(size - 1).getTaskName();
		assertTrue(service.taskList.get(size - 1).getTaskName().equals("Coding"));
	}
	//test to update task description by specifying task ID
	@Test
	@DisplayName("Test to update Task Description")
	void testUpdateTaskDescription() {
		TaskService service = new TaskService();
		Task task = new Task("11111", "Code", "Task Service File");
		service.addTask(task);
		int size = service.taskList.size();
		service.taskList.get(size - 1).getTaskId();
		service.taskList.get(size - 1).getTaskDescription();
		service.updateTaskDescription("Task Service Java File", "11111");
		service.displayTaskList();
		service.taskList.get(size - 1).getTaskDescription();
		assertTrue(service.taskList.get(size - 1).getTaskDescription().equals("Task Service Java File"));
	}
	//test to delete task by specifying task ID
	@Test
	@DisplayName("Test to delete task")
	void testDeleteTask() {
		TaskService service = new TaskService();
		Task task = new Task("11111", "Code", "Task Service File");
		service.addTask(task);
		service.deleteTask("11111");
		ArrayList<Task> taskListEmpty = new ArrayList<Task>();
		service.displayTaskList();
		assertEquals(service.taskList, taskListEmpty, "The task was not deleted.");
	}
	//test to add task with unique task ID
	@Test
	@DisplayName("Test to add task with unique Task ID.")
	void testAddTask() {
		TaskService service = new TaskService();
			
		Task task1 = new Task("1", "Lawn", "Mow and Trim");
		Task task2 = new Task("2", "Shopping", "Cheese, Milk, Eggs");
		Task duplicateTask1 = new Task("1", "Lawn", "Mow and Trim");
			
		assertTrue(service.addTask(task1));
		assertTrue(service.addTask(task2));
			
		assertFalse(service.addTask(duplicateTask1));
	}
}
