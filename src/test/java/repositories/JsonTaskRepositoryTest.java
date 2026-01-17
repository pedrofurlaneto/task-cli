package repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.learning.models.Task;
import org.learning.repositories.ITaskRepository;
import org.learning.repositories.JSONTaskRepository;
import org.learning.types.TaskStatus;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTaskRepositoryTest {
    @TempDir
    Path tempDir;

    @Test
    void mustSaveNewTask() throws IOException {
        Path tempFile = tempDir.resolve("tasks.json");
        ITaskRepository repository = new JSONTaskRepository(tempFile);

        Task task1 = new Task("Buy fish");
        repository.save(task1);

        Task task2 = new Task("Drink 2L of water");
        repository.save(task2);

        assertEquals("Buy fish", repository.getById(1).getDescription());
        assertEquals("Drink 2L of water", repository.getById(2).getDescription());
        assertEquals(TaskStatus.TODO, repository.getById(1).getStatus());
        assertEquals(TaskStatus.TODO, repository.getById(2).getStatus());
    }

    @Test
    void mustRemoveTask() throws IOException {
        Path filePath = tempDir.resolve("tasks.json");
        ITaskRepository repository = new JSONTaskRepository(filePath);

        Task task1 = new Task("Buy fish");
        repository.save(task1);

        Task task2 = new Task("Do the dinner");
        repository.save(task2);

        repository.remove(1);

        assertNull(repository.getById(1));
        assertEquals("Do the dinner", repository.getById(2).getDescription());
    }

    @Test
    void mustListSavedTasks() throws IOException {
        Path filePath = tempDir.resolve("tasks.json");
        ITaskRepository repository = new JSONTaskRepository(filePath);

        Task task1 = new Task("Buy fish");
        repository.save(task1);

        Task task2 = new Task("Do the dinner");
        repository.save(task2);

        Task task3 = new Task("Drink water");
        repository.save(task3);

        repository.remove(2);

        List<Task> tasks = repository.list();

        assertEquals(2, tasks.size());
        assertEquals("Drink water", tasks.get(1).getDescription());
    }

    @Test
    void mustUpdateTaskDescription() throws IOException {
        Path filePath = tempDir.resolve("tasks.json");
        ITaskRepository repository = new JSONTaskRepository(filePath);

        Task task1 = new Task("Buy fish");
        repository.save(task1);

        Task task2 = new Task("Drink water");
        repository.save(task2);

        repository.updateDescription(1, "Go to market");

        assertNotEquals("Buy fish", repository.getById(1).getDescription());
        assertEquals( "Go to market", repository.getById(1).getDescription());
        assertEquals("Drink water", repository.getById(2).getDescription());
    }

    @Test
    void mustUpdateTaskStatus() throws IOException {
        Path filePath = tempDir.resolve("tasks.json");
        ITaskRepository repository = new JSONTaskRepository(filePath);

        Task task1 = new Task("Buy fish");
        repository.save(task1);

        Task task2 = new Task("Drink water");
        repository.save(task2);

        repository.updateDescription(1, "Go to market");
        repository.updateStatus(1, TaskStatus.DONE);
        repository.updateStatus(2, TaskStatus.IN_PROGRESS);

        assertNotEquals(TaskStatus.TODO, repository.getById(1).getStatus());
        assertEquals(TaskStatus.DONE, repository.getById(1).getStatus());
        assertEquals(TaskStatus.IN_PROGRESS, repository.getById(2).getStatus());
    }
}
