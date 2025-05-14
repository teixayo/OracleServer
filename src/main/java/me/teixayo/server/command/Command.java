package me.teixayo.server.command;

import java.util.List;

public abstract class Command {

    private final String name;
    private List<String> aliases;
    private CommandExecutor commandExecuter;
    private TabCompleter tabCompleter;

    public Command(String name, String... aliases) {
        this.name = name;
        if (aliases.length == 0) return;
        this.aliases = List.of(aliases);
    }

    public CommandExecutor getCommandExecuter() {
        return commandExecuter;
    }

    public void setCommandExecuter(CommandExecutor commandExecuter) {
        this.commandExecuter = commandExecuter;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getName() {
        return name;
    }

    public TabCompleter getTabCompleter() {
        return tabCompleter;
    }

    public void setTabCompleter(TabCompleter tabCompleter) {
        this.tabCompleter = tabCompleter;
    }
}
