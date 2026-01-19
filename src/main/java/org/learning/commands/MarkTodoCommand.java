package org.learning.commands;

import org.learning.commands.ICommand;
import org.learning.controllers.TaskController;
import org.learning.types.TaskStatus;

public class MarkTodoCommand implements ICommand {
    private final TaskController controller;

    public MarkTodoCommand(TaskController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: task-cli mark-todo [id]");
            return;
        }

        try {
            int id = Integer.parseInt(args[1]);
            controller.updateStatus(id, TaskStatus.TODO);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}