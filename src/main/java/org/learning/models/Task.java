package org.learning.models;

import org.learning.types.TaskStatus;

import java.util.Date;

public class Task {
    private String description;
    private TaskStatus status;
    final private Date createdAt;
    private Date updatedAt;

    public Task(String name) {
        this.description = name;
        this.status = TaskStatus.TODO;
        this.createdAt = new Date();
    }

    public String getDescription() {
        return description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = new Date();
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
        this.updatedAt = new Date();
    }
}
