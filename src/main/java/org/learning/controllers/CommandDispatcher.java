package org.learning.controllers;

import org.learning.commands.*;
import org.learning.repositories.ITaskRepository;
import org.learning.repositories.JsonTaskRepository;
import org.learning.types.CommandType;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.Map;

public class CommandDispatcher {
    private static final Path filePath = Paths.get("tasks.json");
    private static final Map<CommandType, ICommand> commands = new EnumMap<>(CommandType.class);

    static {
        ITaskRepository repository;

        try {
            repository = new JsonTaskRepository(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TaskController controller = new TaskController(repository);

        commands.put(CommandType.LIST, new ListCommand(controller));
        commands.put(CommandType.ADD, new SaveCommand(controller));
        commands.put(CommandType.UPDATE, new UpdateCommand(controller));
        commands.put(CommandType.DELETE, new DeleteCommand(controller));
        commands.put(CommandType.MARK_IN_PROGRESS, new MarkInProgressCommand(controller));
        commands.put(CommandType.MARK_DONE, new MarkDoneCommand(controller));
    }

    public static void dispatch(String[] args) {
        if (args.length == 0) {
            return;
        }

        CommandType commandType = CommandType.from(args[0]);
        ICommand command = commands.get(commandType);

        command.execute(args);
    }
}
