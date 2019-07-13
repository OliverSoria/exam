package com.exam.developer.catalogs;

public enum CommandsEnum {
    DEPEND("DEPEND"),
    INSTALL("INSTALL"),
    LIST("LIST"),
    REMOVE("REMOVE"),
    END("END");

    private String command;

    CommandsEnum(String command) {
        this.command = command;
    }

    public String command() {
        return command;
    }
}
