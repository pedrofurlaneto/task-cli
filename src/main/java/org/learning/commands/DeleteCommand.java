package org.learning.commands;

import org.learning.controllers.TaskController;

public class DeleteCommand implements ICommand {
    private final TaskController controller;

    public DeleteCommand(TaskController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: task-cli delete [id]");
            return;
        }

        try {
            int id = Integer.parseInt(args[1]);
            controller.remove(id);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
