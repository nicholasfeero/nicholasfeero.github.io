#include "TaskService.h"
#include <iostream>
#include <algorithm>

// Helper method for comparing two strings to validate length and convert to lowercase
bool TaskService::equalsIgnoreCase(const std::string& a, const std::string& b) {
    // Check length of strings
    if (a.size() != b.size()) return false;
    // Increment through each character of string
    for (size_t i = 0; i < a.size(); ++i) {
        // Check if lowercase conversion has a mismatch
        if (std::tolower(static_cast<unsigned char>(a[i])) != std::tolower(static_cast<unsigned char>(b[i])))
            return false;
    }
    return true;
}

// Adds a new task if task list is empty
bool TaskService::addTask(std::shared_ptr<Task> task) {
    // Check for null task
    if (!task) return false;
    // If task list is empty, add task
    if (taskList.empty()) {
        taskList.push_back(task);
        return true;
    }
    // If task list is not empty but has unique task ID, add task to task list
    for (auto& c : taskList) {
        if (equalsIgnoreCase(task->getTaskId(), c->getTaskId())) {
            return false;
        }
    }
    taskList.push_back(task);
    return true;
}

// By utilizing task ID, return task object. If no task ID is found, return default value
std::shared_ptr<Task> TaskService::getTask(const std::string& taskId) const {
    for (auto& t : taskList) {
        if (t && t->getTaskId() == taskId) return t;
    }
    return nullptr;
}

// Delete task from task list by utilizing task ID
void TaskService::deleteTask(const std::string& taskId) {
    // Increment through each task in task list
    for (size_t i = 0; i < taskList.size(); ++i) {
        // If the task ID matches an existing task ID in the task list, delete the task
        if (taskList[i] && taskList[i]->getTaskId() == taskId) {
            taskList.erase(taskList.begin() + i);
            return;
        }
    }
    std::cout << "Task ID: " << taskId << " not found." << std::endl;
}

// Update task name utilizing task ID to find specific task
void TaskService::updateTaskName(const std::string& updatedString, const std::string& taskId) {
    // Increment through each task in task list
    for (size_t i = 0; i < taskList.size(); ++i) {
        // If the task ID matches an existing task ID in the task list, update task name
        if (taskList[i] && taskList[i]->getTaskId() == taskId) {
            taskList[i]->setTaskName(updatedString);
            return;
        }
    }
    std::cout << "Task ID: " << taskId << " not found." << std::endl;
}

// Update task description utilizing task ID to find specific task
void TaskService::updateTaskDescription(const std::string& updatedString, const std::string& taskId) {
    // Increment through each task in task list
    for (size_t i = 0; i < taskList.size(); ++i) {
        // If the task ID matches an existing task ID in the task list, update task description
        if (taskList[i] && taskList[i]->getTaskId() == taskId) {
            taskList[i]->setTaskDescription(updatedString);
            return;
        }
    }
    std::cout << "Task ID: " << taskId << " not found." << std::endl;
}

// Print the task ID, name, and description of each task in task list to console
void TaskService::displayTaskList() const {
    for (auto& t : taskList) {
        if (t) {
            std::cout << "Task ID: " << t->getTaskId() << "\n";
            std::cout << "Task Name: " << t->getTaskName() << "\n";
            std::cout << "Task Description: " << t->getTaskDescription() << "\n";
        }
    }
}
