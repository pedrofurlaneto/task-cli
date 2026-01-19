package controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.learning.models.Task;
import org.learning.repositories.ITaskRepository;
import org.learning.controllers.TaskController;
import org.learning.repositories.JsonTaskRepository;
import org.learning.types.TaskStatus;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskControllerTest {
    @TempDir
    Path tempPath;

    @Test
    void mustCreateTask() throws IOException {
        Path tempFilePath = tempPath.resolve("tasks.json");

        ITaskRepository taskRepository = new JsonTaskRepository(tempFilePath);
        TaskController taskController = new TaskController(taskRepository);
        taskController.create("Buy fish");

        assertEquals("Buy fish", taskController.getById(1).getDescription());
        assertEquals(TaskStatus.TODO, taskController.getById(1).getStatus());
    }

    @Test
    void mustListAllCreatedTasks() throws IOException {
        Path tempFilePath = tempPath.resolve("tasks.json");

        ITaskRepository taskRepository = new JsonTaskRepository(tempFilePath);
        TaskController taskController = new TaskController(taskRepository);

        taskController.create("Buy fish");
        taskController.create("Finish homework");
        taskController.create("Send emails");
        taskController.create("Make the dinner");

        List<Task> tasks = taskController.list();
        assertEquals(4, tasks.size());
    }

    @Test
    void mustRemoveTask() throws IOException {
        Path tempFilePath = tempPath.resolve("tasks.json");

        ITaskRepository taskRepository = new JsonTaskRepository(tempFilePath);
        TaskController taskController = new TaskController(taskRepository);

        taskController.create("Buy fish");
        taskController.create("Finish homework");
        taskController.remove(1);

        assertNull(taskController.getById(1));
    }

    @Test
    void mustUpdateTaskDescription() throws IOException {
        Path tempFilePath = tempPath.resolve("tasks.json");

        ITaskRepository taskRepository = new JsonTaskRepository(tempFilePath);
        TaskController taskController = new TaskController(taskRepository);

        taskController.create("Buy fish");
        taskController.create("Finish homework");
        taskController.create("Finish house cleaning");
        taskController.updateDescription(2, "Study guitar");
        taskRepository.list().forEach(obj -> System.out.println(obj.getId()));

        taskController.updateDescription(3, "Study OOP");


        assertEquals("Study guitar", taskController.getById(2).getDescription());
        assertEquals("Study OOP", taskController.getById(3).getDescription());
    }

    @Test
    void mustUpdateTaskStatus() throws IOException {
        Path tempFilePath = tempPath.resolve("tasks.json");

        ITaskRepository taskRepository = new JsonTaskRepository(tempFilePath);
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
