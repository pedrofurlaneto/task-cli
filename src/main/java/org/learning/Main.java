package org.learning;

import org.learning.controllers.CommandDispatcher;

public class Main {
    public static void main(String[] args) {
        try {
            CommandDispatcher.dispatch(args);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println("Commands: add, update, delete, list, mark-done, mark-in-progress");
        }
    }
}