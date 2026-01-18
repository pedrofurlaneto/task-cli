package org.learning.commands;

import org.learning.controllers.TaskController;
import org.learning.models.Task;
import org.learning.types.TaskStatus;

import java.util.ArrayList;
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

            for (Task task : tasks) {
                System.out.printf("%s: %s%n", task.getDescription(), task.getStatus().getValue());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
