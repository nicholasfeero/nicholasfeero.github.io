#include "gtest/gtest.h"
#include "Task.h"
#include <memory>

// Test if task creation is successful
TEST(TaskTests, ValidTaskCreation) {
    Task t("1", "Dishes", "Dirty and Clean");
    EXPECT_EQ(t.getTaskId(), "1");
    EXPECT_EQ(t.getTaskName(), "Dishes");
    EXPECT_EQ(t.getTaskDescription(), "Dirty and Clean");
}

// Test for task ID character limit | Task Id cannot have more than 10 characters
TEST(TaskTests, TaskIdCharacterLimit) {
    std::string longId = "1234567890987654321";
    EXPECT_THROW(Task(longId, "Dishes", "Dirty and Clean"), std::invalid_argument);
}

// Test for task name character limit | Task name cannot have more than 20 characters
TEST(TaskTests, TaskNameCharacterLimit) {
    std::string longName = "DishesDuringTheDayInTheHousehold";
    EXPECT_THROW(Task("1", longName, "Dirty and Clean"), std::invalid_argument);
}

// Test for task description character limit | Task description cannot have more than 50 characters
TEST(TaskTests, TaskDescriptionCharacterLimit) {
    std::string longDescription = "Dirty Dishes Need To Be Ran Then Put Away Once Clean And If There Are Any Dishes In The Sink Put Them In The Dishwasher";
    EXPECT_THROW(Task("1", "Dishes", longDescription), std::invalid_argument);
}

// Test if task ID is null
TEST(TaskTests, TaskIdNull) {
    EXPECT_THROW(Task("", "Dishes", "Dirty and Clean"), std::invalid_argument);
}

// Test if task name is null
TEST(TaskTests, TaskNameNull) {
    EXPECT_THROW(Task("1", "", "Dirty and Clean"), std::invalid_argument);
}

// Test if task description is null
TEST(TaskTests, TaskDescriptionNull) {
    EXPECT_THROW(Task("1", "Dishes", ""), std::invalid_argument);
}
