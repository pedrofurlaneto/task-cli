package org.learning.repositories;

import org.learning.models.Task;
import org.learning.types.TaskStatus;

import java.util.List;

public interface ITaskRepository {
    void save(Task task);
    void remove(int id);
    void updateDescription(int id, String description);
    void updateStatus(int id, TaskStatus status);
    List<Task> list();
    Task getById(int id);
}
