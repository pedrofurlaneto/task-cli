package org.learning.commands;

import org.learning.controllers.TaskController;
import org.learning.types.TaskStatus;

public class MarkInProgressCommand implements ICommand {
    private final TaskController controller;

    public MarkInProgressCommand(TaskController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: task-cli mark-in-progress [id]");
            return;
        }

        try {
            int id = Integer.parseInt(args[1]);
            controller.updateStatus(id, TaskStatus.IN_PROGRESS);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}