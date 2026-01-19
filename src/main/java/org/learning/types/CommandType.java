package org.learning.types;

public enum CommandType {
    ADD("add"),
    UPDATE("update"),
    DELETE("delete"),
    LIST("list"),
    MARK_TODO("mark-todo"),
    MARK_IN_PROGRESS("mark-in-progress"),
    MARK_DONE("mark-done");

    private final String cliName;

    CommandType(String cliName) {
        this.cliName = cliName;
    }

    public static CommandType from(String input) {
        for (CommandType command : values()) {
            if (command.cliName.equalsIgnoreCase(input)) {
                return command;
            }
        }

        throw new IllegalArgumentException("Invalid command: " + input);
    }
}
