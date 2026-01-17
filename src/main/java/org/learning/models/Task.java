package org.learning.models;

import org.learning.types.TaskStatus;

import java.util.Date;

public class Task {
    private String description;
    private TaskStatus status;

    public Task(String description) {
        this.description = description;
        this.status = TaskStatus.TODO;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
