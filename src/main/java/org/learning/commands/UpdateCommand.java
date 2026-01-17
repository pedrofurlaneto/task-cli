package org.learning.commands;

import org.learning.controllers.TaskController;

public class UpdateCommand implements ICommand {

    private final TaskController controller;

    public UpdateCommand(TaskController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: task-tracker update [id] [new description]");
            return;
        }

        try {
            int id = Integer.parseInt(args[1]);
            controller.updateDescription(id, args[2]);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
