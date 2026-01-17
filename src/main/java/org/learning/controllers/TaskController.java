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

    public int create(String name) throws IOException {
        Task newTask = new Task(name);

        return this.repository.save(newTask);
    }

    public void updateDescription(int id, String description) throws IOException {
        this.repository.updateDescription(id, description);
    }

    public void updateStatus(int id, TaskStatus status) throws IOException {
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
}