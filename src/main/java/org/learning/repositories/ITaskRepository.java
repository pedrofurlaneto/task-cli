package org.learning.repositories;

import org.learning.models.Task;
import org.learning.types.TaskStatus;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ITaskRepository {
    int save(Task task) throws IOException;
    void remove(int id) throws IOException;
    void updateDescription(int id, String description) throws IOException;
    void updateStatus(int id, TaskStatus status) throws IOException;
    List<Task> list() throws IOException;
    Task getById(int id) throws IOException;
}
