package org.learning.controllers;

import org.learning.models.Task;
import org.learning.repositories.ITaskRepository;
import org.learning.types.TaskStatus;

import java.util.List;

public class TaskController {
    private final ITaskRepository repository;

    public TaskController(ITaskRepository repository) {
        this.repository = repository;
    }

    public Task create(String name) {
        Task newTask = new Task(name);

        this.repository.save(newTask);
        return newTask;
    }

    public void updateDescription(int id, String description) {
        this.repository.updateDescription(id, description);
    }

    public void updateStatus(int id, TaskStatus status) {
        this.repository.updateStatus(id, status);
    }

    public Task getById(int id) {
        return this.repository.getById(id);
    }

    public void remove(int id) {
        repository.remove(id);
    }

    public List<Task> list() {
        return this.repository.list();
    }
}