package me.teixayo.server.command;

import java.util.HashMap;
import java.util.List;

public class CommandManager {
    private final HashMap<String, Command> commandMap;

    public CommandManager() {
        commandMap = new HashMap<>();
    }

    public void register(Command command) {
        commandMap.put(command.getName().toLowerCase(), command);
        List<String> aliases = command.getAliases();
        if (aliases == null) return;
        for (String alias : aliases) {
            commandMap.put(alias.toLowerCase(), command);
        }
    }

    public void unregister(Command command) {
        commandMap.remove(command.getName().toLowerCase());
        List<String> aliases = command.getAliases();
        if (aliases == null) return;
        for (String alias : aliases) {
            commandMap.remove(alias.toLowerCase());
        }
    }

    public void unregister(String command) {
        commandMap.remove(command.toLowerCase());
    }

    public HashMap<String, Command> getCommandMap() {
        return commandMap;
    }

    public Command getCommand(String command) {
        return commandMap.get(command.toLowerCase());
    }
}
