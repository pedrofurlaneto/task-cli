package org.learning.commands;

import org.learning.controllers.TaskController;
import org.learning.models.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ListCommand implements ICommand {
    private final TaskController controller;

    public ListCommand(TaskController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 2) {
            System.out.println("Usage: task-cli list [todo | in-progress | done]");
            return;
        }

        try {
            List<Task> tasks;

            if (args.length > 1) {
                tasks = controller.list(args[1]);
            } else {
                tasks = controller.list();
            }

            System.out.println("ID | Description | Status | Created at | Updated at");
            for (Task task : tasks) {
                System.out.printf("%d | %s | %s | %s | %s%n", task.getId(), task.getDescription(), task.getStatus().getValue(), formatDate(task.getCreatedAt()), formatDate(task.getUpdatedAt()));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    String formatDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return date.format(formatter);
    }
}
