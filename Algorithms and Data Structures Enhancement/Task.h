#pragma once
#include <string>
#include <stdexcept>

class Task {
public:
    // Constructor
    Task(const std::string& taskId, const std::string& taskName, const std::string& taskDescription);

    // Getters for ID, name, and description of task objects
    const std::string& getTaskId() const noexcept;
    const std::string& getTaskName() const noexcept;
    const std::string& getTaskDescription() const noexcept;

    // Setters for name and description of task objects (ID is immutable)
    void setTaskName(const std::string& newTaskName);
    void setTaskDescription(const std::string& newTaskDescription);

    // Validation character limits (public so tests can reference if needed) | avoids magic numbers in code
    static constexpr size_t MAX_ID_LEN = 10;
    static constexpr size_t MAX_NAME_LEN = 20;
    static constexpr size_t MAX_DESC_LEN = 50;

private:
    // Fields used to store task data internally
    std::string taskId_;
    std::string taskName_;
    std::string taskDescription_;

    // Validate input strings to ensure the string is not null and does not exceed character limit
    static void validateString(const std::string& s, size_t maxLen, const char* fieldName);
};
