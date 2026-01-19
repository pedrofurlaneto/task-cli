package org.learning.controllers;

import org.learning.models.Task;
import org.learning.repositories.ITaskRepository;
import org.learning.types.TaskStatus;

import java.io.IOException;
import java.util.List;

public class TaskController {
    private final ITaskRepository repository;

    public TaskController(ITaskRepository repository) {
        this.repository = repository;
    }

    public int create(String description) throws IOException {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Task description can't be empty");
        }

        int id = repository.generateNextId();
        Task newTask = new Task(id, description);

        return this.repository.save(newTask);
    }

    public void updateDescription(int id, String description) throws IOException {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Task description can't be empty");
        }

        this.repository.updateDescription(id, description);
    }

    public void updateStatus(int id, TaskStatus status) throws IOException {
        if (status == null) {
            throw new IllegalArgumentException("Task status can't be empty");
        }

        this.repository.updateStatus(id, status);
    }

    public Task getById(int id) throws IOException {
        return this.repository.getById(id);
    }

    public void remove(int id) throws IOException {
        repository.remove(id);
    }

    public List<Task> list() throws IOException {
        return this.repository.list();
    }

    public List<Task> list(String status) throws IOException {
        return this.repository.list(TaskStatus.from(status));
    }
}