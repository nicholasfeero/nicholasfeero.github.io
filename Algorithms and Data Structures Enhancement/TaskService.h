#pragma once
#include "Task.h"
#include <vector>
#include <memory>
#include <string>

class TaskService {
public:
    // Constructor
    TaskService() = default;

    // Store tasks in a vector similar to Java's ArrayList
    std::vector<std::shared_ptr<Task>> taskList;

    // Attempt to add task to task list. If not possible, null
    bool addTask(std::shared_ptr<Task> task);
    std::shared_ptr<Task> getTask(const std::string& taskId) const;
    // Attempt to delete task by utilizing task ID
    void deleteTask(const std::string& taskId);
    // Attempt to update task name by utilizing task ID
    void updateTaskName(const std::string& updatedString, const std::string& taskId);
    // Attempt to update task description by utilizing task ID
    void updateTaskDescription(const std::string& updatedString, const std::string& taskId);

    // Print all tasks in task list to console
    void displayTaskList() const;
private:
    // Compare two strings (with case-insensitivity) and determine if they're duplicates by utilizing task ID
    static bool equalsIgnoreCase(const std::string& a, const std::string& b);
};
