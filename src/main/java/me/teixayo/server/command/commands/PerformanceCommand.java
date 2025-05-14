package me.teixayo.server.command.commands;

import me.teixayo.server.Server;
import me.teixayo.server.command.Command;
import me.teixayo.server.command.CommandExecutor;
import me.teixayo.server.command.TabCompleter;
import me.teixayo.server.entity.player.Player;
import me.teixayo.server.protocol.netty.PacketPrepender;
import me.teixayo.server.scheduler.SchedulerTask;

import java.text.DecimalFormat;
import java.util.HashMap;

public class PerformanceCommand extends Command implements CommandExecutor, TabCompleter {

    private final HashMap<Player, SchedulerTask> profilingTask = new HashMap<>();
    private final DecimalFormat format = new DecimalFormat("0.00");

    public PerformanceCommand() {
        super("performance", "pt", "pr");
        setCommandExecuter(this);
        setTabCompleter(this);
    }

    @Override
    public void apply(Player sender, String[] args) {

        SchedulerTask task = profilingTask.get(sender);
        if (task != null) task.cancel();

        task = Server.get().getSchedulerManager().sync().runTimer(schedulerTask -> {
            long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            double present = (double) usedMemory / Runtime.getRuntime().totalMemory() * 100.0;
            float currentTPS = (float) Server.get().getCurrentTPS();

            if (currentTPS >= 20 && currentTPS <= 20.02) {
                currentTPS = 20.0f;
            }
            String header = "\n&eTPS: &a" + format.format(currentTPS) +
                    "&7, &eMSPT: &a" + format.format(Server.get().getCurrentMSPT());

            String footer = "\n&d-     &eRAM: &a" + format.format(usedMemory / 1048576.0) + "&7/&a" + format.format(Runtime.getRuntime().totalMemory() / 1048576.0) + " MB&7, &a" + format.format(present) + "%" +
                    "&7, &eCPU: &a" + format.format(Server.get().getCpuUsage()) + "%" +
                    "&7, &ePING: &a " + format.format(sender.getPlayerConnection().getPing()) + " MS     &d-\n\n" +
                    "&eBANDWIDTH: &a" + format.format(PacketPrepender.getUsedBandwidth(sender.getPlayerConnection().getChannel().channel()) / (1024.0f * 1024.0f)) + " MB\n";

            sender.setHeaderAndFooterColorized(header, footer);
        }, 20, 20);

        profilingTask.put(sender, task);
    }

    @Override
    public String[] tabComplete(Player sender, String[] args) {
        if (args.length == 0) {
            return new String[]{"HOH,AAAAAA", "BBB"};
        }
        return null;
    }
}
