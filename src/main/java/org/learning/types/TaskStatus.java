package org.learning.types;

public enum TaskStatus {
    TODO("todo"),
    IN_PROGRESS("in-progress"),
    DONE("done");

    private final String status;

    TaskStatus(String status) {
        this.status = status;
    }

    public String getValue() {
        return status;
    }

    public static TaskStatus from(String input) {
        String normalized = input.toLowerCase().replace("_", "-");

        for (TaskStatus taskStatus : values()) {
            if (taskStatus.status.equalsIgnoreCase(normalized)) {
                return taskStatus;
            }
        }

        throw new IllegalArgumentException("Invalid status: " + input);
    }
}
