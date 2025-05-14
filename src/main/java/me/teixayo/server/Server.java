package me.teixayo.server;

import com.sun.management.OperatingSystemMXBean;
import lombok.Getter;
import me.teixayo.server.chunk.World;
import me.teixayo.server.command.CommandManager;
import me.teixayo.server.command.commands.*;
import me.teixayo.server.entity.player.Player;
import me.teixayo.server.event.EventManager;
import me.teixayo.server.listener.InfoListener;
import me.teixayo.server.listener.SimplePacketListener;
import me.teixayo.server.memory.MemoryManagement;
import me.teixayo.server.networking.NettyHandler;
import me.teixayo.server.scheduler.SchedulerManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.management.ManagementFactory;
import java.util.Collection;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.LockSupport;
import java.util.function.LongSupplier;

@Getter
public class Server implements Runnable {

    private static Server INSTANCE;
    private final SchedulerManager schedulerManager;
    private final EventManager eventManager;
    private final NettyHandler nettyHandler;
    private final CommandManager commandManager;
    private final ConcurrentHashMap<UUID, Player> players;
    private final World defaultWorld = new World();
    private final Logger logger;
    private final long startTime = System.nanoTime();
    private long ELAPSED_TICKS = 0;
    private boolean isRunning;
    private double currentTPS;
    private double currentMSPT;
    private double cpuUsage;
    private long totalTime;
    private long tick = 0;

    public Server() {
        long start = System.currentTimeMillis();
        INSTANCE = this;
        logger = LogManager.getLogger("core");

        logger.info("Loading...");
        players = new ConcurrentHashMap<>();
        schedulerManager = new SchedulerManager();
        eventManager = new EventManager();
        commandManager = new CommandManager();
        commandManager.register(new PerformanceCommand());
        commandManager.register(new SetExpCommand());
        commandManager.register(new GmsCommand());
        commandManager.register(new GUICommand());
        commandManager.register(new NPCCommand());

        eventManager.register(new InfoListener());

        String address = "0.0.0.0";
        int port = Integer.parseInt(System.getenv("PORT"));
        nettyHandler = new NettyHandler(address, port);

        logger.info("Starting Minecraft server on " + address + ":" + port);
        nettyHandler.addListener(new SimplePacketListener());


        schedulerManager.async().runTimer(schedulerTask -> {
            OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

            cpuUsage = operatingSystemMXBean.getProcessCpuLoad() * 100.0;
        }, 20, 20);

        schedulerManager.async().runTimer(schedulerTask -> {
            defaultWorld.loadQueueChunks();
            defaultWorld.finalizeForLoadingChunks();
        }, 1, 1);

        long end = System.currentTimeMillis();

        logger.info("Done (" + ((end - start) / 1000.0) + "s)!");
    }


    public static Server get() {
        return INSTANCE;
    }

    public Collection<Player> getPlayers() {
        return players.values();
    }

    public Player getPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public void addPlayer(Player player) {
        players.put(player.getUniqueID(), player);
    }

    public void sleep() {

        Queue<Runnable> asyncCallables = schedulerManager.getAsyncCallables();


        long nextTickNs = startTime + (50000000 * tick);
        while (true) {

            if (!asyncCallables.isEmpty()) {
                Runnable poll = asyncCallables.poll();
                schedulerManager.getExecutor().execute(poll);
                continue;
            }
            LockSupport.parkNanos(850_000);

            long currentNs = System.nanoTime();

            if (currentNs >= nextTickNs) {
                break;
            }

        }
        tick++;

    }

    @Override
    public void run() {
        long start = System.nanoTime();

        tick();
        long endTick = System.nanoTime();
        currentMSPT = (endTick - start) / 1E6;

        sleep();
//        sleep((long) (5E7 - (endTick-start)));

        long end = System.nanoTime();
        ELAPSED_TICKS++;
        totalTime += end - start;

        if (ELAPSED_TICKS % 20 == 0) {
            currentTPS = 1 / (totalTime / 2E10);
            totalTime = 0;
        }
    }


    private void tick() {


        for (Player player : getPlayers()) {
            player.tick();
        }
        schedulerManager.call();
    }

    public void start() {
        isRunning = true;
        while (isRunning) {
            run();
        }
        schedulerManager.shutdown();
    }

    public void stop() {
        isRunning = false;
        MemoryManagement.freeAll();
    }


}
