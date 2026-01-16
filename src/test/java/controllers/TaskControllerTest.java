package controllers;

import org.junit.jupiter.api.Test;
import org.learning.models.Task;
import org.learning.repositories.ITaskRepository;
import org.learning.repositories.InMemoryTaskRepository;
import org.learning.controllers.TaskController;
import org.learning.types.TaskStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskControllerTest {
    @Test
    void mustCreateTask() {
        ITaskRepository taskRepository = new InMemoryTaskRepository();
        TaskController taskController = new TaskController(taskRepository);
        Task newTask = taskController.create("Buy fish");

        assertEquals("Buy fish", newTask.getDescription());
        assertEquals(TaskStatus.TODO, newTask.getStatus());
    }

    @Test
    void mustListAllCreatedTasks() {
        ITaskRepository taskRepository = new InMemoryTaskRepository();
        TaskController taskController = new TaskController(taskRepository);

        taskController.create("Buy fish");
        taskController.create("Finish homework");
        taskController.create("Send emails");
        taskController.create("Make the dinner");

        List<Task> tasks = taskController.list();
        assertEquals(4, tasks.size());
    }

    @Test
    void mustRemoveTask() {
        ITaskRepository taskRepository = new InMemoryTaskRepository();
        TaskController taskController = new TaskController(taskRepository);

        taskController.create("Buy fish");
        taskController.create("Finish homework");
        taskController.remove(1);

        assertNotEquals("Buy fish", taskController.getById(1).getDescription());
    }

    @Test
    void mustUpdateTaskDescription() {
        ITaskRepository taskRepository = new InMemoryTaskRepository();
        TaskController taskController = new TaskController(taskRepository);

        taskController.create("Buy fish");
        taskController.create("Finish homework");
        taskController.create("Finish house cleaning");
        taskController.updateDescription(2, "Study guitar");
        taskController.updateDescription(3, "Study OOP");

        assertEquals("Study guitar", taskController.getById(2).getDescription());
        assertEquals("Study OOP", taskController.getById(3).getDescription());
    }

    @Test
    void mustUpdateTaskStatus() {
        ITaskRepository taskRepository = new InMemoryTaskRepository();
        TaskController taskController = new TaskController(taskRepository);

        taskController.create("Buy fish");
        taskController.create("Finish homework");
        taskController.create("Finish house cleaning");
        taskController.updateStatus(1, TaskStatus.DONE);
        taskController.updateStatus(3, TaskStatus.IN_PROGRESS);

        assertEquals(TaskStatus.DONE, taskController.getById(1).getStatus());
        assertEquals(TaskStatus.TODO, taskController.getById(2).getStatus());
        assertEquals(TaskStatus.IN_PROGRESS, taskController.getById(3).getStatus());
    }
}
