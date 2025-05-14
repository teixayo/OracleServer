package me.teixayo.server.command;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public abstract class Command {

    @Getter
    private final String name;
    @Getter
    private List<String> aliases;
    @Setter
    @Getter
    private CommandExecutor commandExecuter;
    @Setter
    @Getter
    private TabCompleter tabCompleter;

    public Command(String name, String... aliases) {
        this.name = name;
        if (aliases.length == 0) return;
        this.aliases = List.of(aliases);
    }

}
