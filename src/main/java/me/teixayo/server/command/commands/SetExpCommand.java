package me.teixayo.server.command.commands;

import me.teixayo.server.command.Command;
import me.teixayo.server.command.CommandExecutor;
import me.teixayo.server.entity.player.Player;

public class SetExpCommand extends Command implements CommandExecutor {

    public SetExpCommand() {
        super("exp", "setexp");
        setCommandExecuter(this);
    }

    @Override
    public void apply(Player sender, String[] args) {
        if (args.length != 3) {
            sender.sendMessageColorized("&CUsage: /exp <expBar> <level> <totalExp>");
            return;
        }
        float expBar = Float.parseFloat(args[0]);
        int level = Integer.parseInt(args[1]);
        int totalExp = Integer.parseInt(args[2]);
        sender.setExperience(expBar, level, totalExp);
        sender.sendMessageColorized("&aYour exp has been set to what you wanted.");

    }
}
