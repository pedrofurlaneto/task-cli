package org.learning.repositories;

import jakarta.json.*;
import org.learning.models.Task;
import org.learning.storages.ITaskStorage;
import org.learning.types.TaskStatus;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public class JsonTaskRepository implements ITaskRepository {
    private final ITaskStorage storage;
    int currentId;

    public JsonTaskRepository(ITaskStorage storage) throws IOException {
        this.storage = storage;

        if (this.storage.exists()) {
            this.storage.initialize();
        }

        this.currentId = this.storage.readAll().size() + 1;
    }

    @Override
    public int save(Task task) throws IOException {
        List<Task> tasks = storage.readAll();
        tasks.add(task);
        storage.writeAll(tasks);
        this.currentId++;

        return task.getId();
    }

    @Override
    public void remove(int id) throws IOException {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid Task ID argument");
        }

        List<Task> tasks = storage.readAll();
        boolean removed = tasks.removeIf(task -> task.getId() == id);

        if (!removed) {
            throw new NoSuchElementException(String.format("Task with ID %d not found", id));
        }

        storage.writeAll(tasks);
        this.currentId--;
    }

    @Override
    public void updateDescription(int id, String description) throws IOException {
        Task taskToUpdate = getById(id);
        taskToUpdate.setDescription(description);

        updateTask(id, taskToUpdate);
    }

    @Override
    public void updateStatus(int id, TaskStatus status) throws IOException {
        Task taskToUpdate = getById(id);
        taskToUpdate.setStatus(status);

        updateTask(id, taskToUpdate);
    }

    private void updateTask(int id, Task taskToUpdate) throws IOException {
        List<Task> tasks = storage.readAll();
        List<Task> updatedTasks = tasks.stream()
                .map(task -> {
                    if (task.getId() == id) {
                        task = taskToUpdate;
                    }

                    return task;
                }).toList();

        storage.writeAll(updatedTasks);
    }

    @Override
    public List<Task> list() throws IOException {
        return storage.readAll();
    }

    @Override
    public List<Task> list(TaskStatus status) throws IOException {
        List<Task> tasks = list();

        return tasks.stream()
                .filter(task -> task.getStatus() == status)
                .toList();
    }

    @Override
    public Task getById(int id) throws IOException {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid Task ID argument");
        }

        List<Task> tasks = storage.readAll();

        return tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(String.format("Task with ID %d not found", id)));
    }

    @Override
    public int generateNextId() {
        return this.currentId;
    }
}
