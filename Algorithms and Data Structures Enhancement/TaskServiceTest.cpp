#include "gtest/gtest.h"
#include "TaskService.h"
#include "Task.h"
#include <memory>
#include <vector>

// Test creation of tasks with unique task ID using TaskService methods
TEST(TaskServiceTests, ValidTaskCreation) {
    Task t("1", "Dishes", "Dirty and Clean");
    EXPECT_EQ(t.getTaskId(), "1");
    EXPECT_EQ(t.getTaskName(), "Dishes");
    EXPECT_EQ(t.getTaskDescription(), "Dirty and Clean");
}

// Test to update task name by specifying task ID
TEST(TaskServiceTests, UpdateTaskName) {
    TaskService service;
    auto task = std::make_shared<Task>("11111", "Code", "Task Service File");
    ASSERT_TRUE(service.addTask(task));
    size_t size = service.taskList.size();
    service.updateTaskName("Coding", "11111");
    ASSERT_EQ(service.taskList[size - 1]->getTaskName(), "Coding");
}

// Test to update task description by specifying task ID
TEST(TaskServiceTests, UpdateTaskDescription) {
    TaskService service;
    auto task = std::make_shared<Task>("11111", "Code", "Task Service File");
    ASSERT_TRUE(service.addTask(task));
    size_t size = service.taskList.size();
    service.updateTaskDescription("Task Service C++ File", "11111");
    ASSERT_EQ(service.taskList[size - 1]->getTaskDescription(), "Task Service C++ File");
}

// Test to delete task by specifying task ID
TEST(TaskServiceTests, DeleteTask) {
    TaskService service;
    auto task = std::make_shared<Task>("11111", "Code", "Task Service File");
    ASSERT_TRUE(service.addTask(task));
    service.deleteTask("11111");
    std::vector<std::shared_ptr<Task>> emptyVec;
    EXPECT_EQ(service.taskList, emptyVec);
}

// Test to add task with unique task ID
TEST(TaskServiceTests, AddTaskWithUniqueId) {
    TaskService service;
    auto task1 = std::make_shared<Task>("1", "Lawn", "Mow and Trim");
    auto task2 = std::make_shared<Task>("2", "Shopping", "Cheese, Milk, Eggs");
    auto duplicateTask1 = std::make_shared<Task>("1", "Lawn", "Mow and Trim");

    EXPECT_TRUE(service.addTask(task1));
    EXPECT_TRUE(service.addTask(task2));
    EXPECT_FALSE(service.addTask(duplicateTask1));
}
