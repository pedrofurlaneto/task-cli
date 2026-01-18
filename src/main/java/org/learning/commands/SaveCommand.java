package org.learning.commands;

import org.learning.controllers.TaskController;

public class SaveCommand implements ICommand {
    private final TaskController controller;

    public SaveCommand(TaskController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: task-cli add [task description]");
            return;
        }

        try {
            int id = controller.create(args[1]);
            System.out.printf("Task added successfully (ID: %d)%n", id);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
