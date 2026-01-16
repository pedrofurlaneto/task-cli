package org.learning.repositories;

import org.learning.models.Task;
import org.learning.types.TaskStatus;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskRepository implements ITaskRepository {
    private final List<Task> tasks;

    public InMemoryTaskRepository() {
        this.tasks = new ArrayList<>();
    }

    @Override
    public void save(Task task) {
        tasks.add(task);
    }

    @Override
    public void remove(int id) {
        if (id > tasks.size()) {
            return;
        }

        int index = id - 1;

        if (index < 0) {
            return;
        }

        tasks.remove(index);
    }

    @Override
    public void updateDescription(int id, String description) {
        this.getById(id).setDescription(description);
    }

    @Override
    public void updateStatus(int id, TaskStatus status) {
        this.getById(id).setStatus(status);
    }

    @Override
    public List<Task> list() {
        return tasks;
    }

    @Override
    public Task getById(int id) {
        if (id > tasks.size()) {
            return null;
        }

        int index = id - 1;

        if (index < 0) {
            return null;
        }

        return this.tasks.get(index);
    }
}
