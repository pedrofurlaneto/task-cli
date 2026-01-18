package org.learning.repositories;

import org.learning.models.Task;
import org.learning.types.TaskStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryTaskRepository implements ITaskRepository {
    private final List<Task> tasks = new ArrayList<>();

    @Override
    public int save(Task task) {
        tasks.add(task);

        return tasks.size();
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
    public List<Task> list(TaskStatus status) throws IOException
    {
        return tasks.stream()
                .filter(task -> task.getStatus() == status)
                .collect(Collectors.toList());
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
