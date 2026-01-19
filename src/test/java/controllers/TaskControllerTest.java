package controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.learning.models.Task;
import org.learning.repositories.ITaskRepository;
import org.learning.controllers.TaskController;
import org.learning.repositories.JsonTaskRepository;
import org.learning.storages.ITaskStorage;
import org.learning.storages.JsonTaskStorage;
import org.learning.types.TaskStatus;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TaskControllerTest {
    @TempDir
    Path tempPath;

    @Test
    void mustCreateTask() throws IOException {
        Path tempFilePath = tempPath.resolve("tasks.json");

        ITaskStorage storage = new JsonTaskStorage(tempFilePath);
        ITaskRepository repository = new JsonTaskRepository(storage);
        TaskController controller = new TaskController(repository);

        controller.create("Buy fish");

        assertEquals("Buy fish", controller.getById(1).getDescription());
        assertEquals(TaskStatus.TODO, controller.getById(1).getStatus());
    }

    @Test
    void mustListAllCreatedTasks() throws IOException {
        Path tempFilePath = tempPath.resolve("tasks.json");

        ITaskStorage storage = new JsonTaskStorage(tempFilePath);
        ITaskRepository repository = new JsonTaskRepository(storage);
        TaskController controller = new TaskController(repository);

        controller.create("Buy fish");
        controller.create("Finish homework");
        controller.create("Send emails");
        controller.create("Make the dinner");

        List<Task> tasks = controller.list();
        assertEquals(4, tasks.size());
    }

    @Test
    void mustListWithFilter() throws IOException {
        Path tempFilePath = tempPath.resolve("tasks.json");

        ITaskStorage storage = new JsonTaskStorage(tempFilePath);
        ITaskRepository repository = new JsonTaskRepository(storage);
        TaskController controller = new TaskController(repository);

        controller.create("Buy fish");
        controller.create("Finish homework");
        controller.create("Send emails");
        controller.create("Make the dinner");

        controller.updateStatus(2, TaskStatus.IN_PROGRESS);
        controller.updateStatus(3, TaskStatus.IN_PROGRESS);
        controller.updateStatus(4, TaskStatus.DONE);

        List<Task> tasksTodo = controller.list("todo");
        List<Task> tasksInProgress = controller.list("in-progress");
        List<Task> tasksDone = controller.list("done");

        assertEquals(1, tasksTodo.size());
        assertEquals(2, tasksInProgress.size());
        assertEquals(1, tasksDone.size());
    }


    @Test
    void mustRemoveTask() throws IOException {
        Path tempFilePath = tempPath.resolve("tasks.json");

        ITaskStorage storage = new JsonTaskStorage(tempFilePath);
        ITaskRepository repository = new JsonTaskRepository(storage);
        TaskController controller = new TaskController(repository);

        controller.create("Buy fish");
        controller.create("Finish homework");
        controller.remove(1);

        assertThrows(NoSuchElementException.class, () -> controller.getById(1), "Task with ID 1 not found");
    }

    @Test
    void mustUpdateTaskDescription() throws IOException {
        Path tempFilePath = tempPath.resolve("tasks.json");

        ITaskStorage storage = new JsonTaskStorage(tempFilePath);
        ITaskRepository repository = new JsonTaskRepository(storage);
        TaskController controller = new TaskController(repository);

        controller.create("Buy fish");
        controller.create("Finish homework");
        controller.create("Finish house cleaning");
        controller.updateDescription(2, "Study guitar");
        repository.list().forEach(obj -> System.out.println(obj.getId()));

        controller.updateDescription(3, "Study OOP");

        assertEquals("Study guitar", controller.getById(2).getDescription());
        assertEquals("Study OOP", controller.getById(3).getDescription());
        assertThrows(NoSuchElementException.class, () -> controller.updateDescription(6,"Example"));
    }

    @Test
    void mustUpdateTaskStatus() throws IOException {
        Path tempFilePath = tempPath.resolve("tasks.json");

        ITaskStorage storage = new JsonTaskStorage(tempFilePath);
        ITaskRepository repository = new JsonTaskRepository(storage);
        TaskController controller = new TaskController(repository);

        controller.create("Buy fish");
        controller.create("Finish homework");
        controller.create("Finish house cleaning");
        controller.updateStatus(1, TaskStatus.DONE);
        controller.updateStatus(3, TaskStatus.IN_PROGRESS);

        assertEquals(TaskStatus.DONE, controller.getById(1).getStatus());
        assertEquals(TaskStatus.TODO, controller.getById(2).getStatus());
        assertEquals(TaskStatus.IN_PROGRESS, controller.getById(3).getStatus());

        controller.updateStatus(1, TaskStatus.TODO);
        assertEquals(TaskStatus.TODO, controller.getById(1).getStatus());
    }
}
