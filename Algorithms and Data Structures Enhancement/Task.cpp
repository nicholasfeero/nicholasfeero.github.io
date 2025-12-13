#include "Task.h"
#include <sstream>

// Criteria for creating an object (Task) of the class
Task::Task(const std::string& taskId, const std::string& taskName, const std::string& taskDescription)
    : taskId_(taskId), taskName_(taskName), taskDescription_(taskDescription)
{

    // Check if task ID meets character limit or is not null
    validateString(taskId_, MAX_ID_LEN, "Task ID");
    // Check if task name meets character limit or is not null
    validateString(taskName_, MAX_NAME_LEN, "Task Name");
    // Check if task description meets character limit or is not null
    validateString(taskDescription_, MAX_DESC_LEN, "Task Description");
}

// Getter methods for each variable
const std::string& Task::getTaskId() const noexcept { return taskId_; }
const std::string& Task::getTaskName() const noexcept { return taskName_; }
const std::string& Task::getTaskDescription() const noexcept { return taskDescription_; }

// Setter methods for taskName and taskDescription respectively to be used in TaskService
// There does not exist a setter method for taskId as this variable shouldn't be altered or updated
void Task::setTaskName(const std::string& newTaskName) {
    validateString(newTaskName, MAX_NAME_LEN, "Task Name");
    taskName_ = newTaskName;
}
void Task::setTaskDescription(const std::string& newTaskDescription) {
    validateString(newTaskDescription, MAX_DESC_LEN, "Task Description");
    taskDescription_ = newTaskDescription;
}

// Helper method with error handling for character limit or null validation
void Task::validateString(const std::string& s, size_t maxLen, const char* fieldName) {
    if (s.empty() || s.size() > maxLen) {
        std::ostringstream oss;
        oss << fieldName << " is invalid";
        throw std::invalid_argument(oss.str());
    }
}
