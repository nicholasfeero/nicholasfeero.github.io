/*
 * Nicholas Feero
 * CS-320: Software Test & Automation
 * Module #4 Milestone | TaskTest.java
 * Professor Toledo
 * February 2nd, 2025
*/
//creation of TaskTest class

package Tests;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TaskTest {
	//test if task creation is successful
	@Test
	void testTaskClass() {
		Task task = new Task("1", "Dishes", "Dirty and Clean");
		assertTrue(task.getTaskId().equals("1"));
		assertTrue(task.getTaskName().equals("Dishes"));
		assertTrue(task.getTaskDescription().equals("Dirty and Clean"));
	}
	//test for task ID character limit
	@Test
	@DisplayName("Task ID cannot have more than 10 characters")
	void testTaskIDCharacterLimit() {
		Assertions.assertThrows(IllegalArgumentException.class,  () -> {
			new Task("1234567890987654321", "Dishes", "Dirty and Clean");
		});	
	}
	//test for task name character limit
	@Test
	@DisplayName("Task Name cannot have more than 20 characters")
	void testTaskNameCharacterLimit() {
		Assertions.assertThrows(IllegalArgumentException.class,  () -> {
			new Task("1", "DishesDuringTheDayInTheHousehold", "Dirty and Clean");
		});	
	}
	//test for task description character limit
	@Test
	@DisplayName("Task Description cannot have more than 50 characters")
	void testTaskDescriptionCharacterLimit() {
		Assertions.assertThrows(IllegalArgumentException.class,  () -> {
			new Task("1", "Dishes", "Dirty Dishes Need To Be Ran Then Put Away Once Clean And If There Are Any Dishes In The Sink Put Them In The Dishwasher");
		});	
	}
	//test if task ID is null
	@Test
	@DisplayName("Task ID shall not be null")
	void testTaskIDNull() {
		Assertions.assertThrows(IllegalArgumentException.class,  () -> {
			new Task(null, "Dishes", "Dirty and Clean");
		});	
	}
	//test if task name is null
	@Test
	@DisplayName("Task Name shall not be null")
	void testTaskNameNull() {
		Assertions.assertThrows(IllegalArgumentException.class,  () -> {
			new Task("1", null, "Dirty and Clean");
		});	
	}
	//test if task description is null
	@Test
	@DisplayName("Task Description shall not be null")
	void testTaskDescriptionNull() {
		Assertions.assertThrows(IllegalArgumentException.class,  () -> {
			new Task("1", "Dishes", null);
		});	
	}
}
