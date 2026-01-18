package org.learning.commands;

import org.learning.controllers.TaskController;
import org.learning.models.Task;

import java.util.List;

public class ListCommand implements ICommand {
    private final TaskController controller;

    public ListCommand(TaskController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: task-cli list");
            return;
        }

        try {
            List<Task> tasks = controller.list();

            for (Task task : tasks) {
                System.out.printf("%s $%s%n", task.getDescription(), task.getStatus());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
