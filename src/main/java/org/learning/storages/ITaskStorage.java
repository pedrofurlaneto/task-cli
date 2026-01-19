package org.learning.storages;

import org.learning.models.Task;

import java.io.IOException;
import java.util.List;

public interface ITaskStorage {
    List<Task> readAll() throws IOException;
    void writeAll(List<Task> tasks) throws IOException;
    boolean exists();
    void initialize() throws IOException;
}
