package me.teixayo.server.scheduler;

import lombok.Getter;
import me.teixayo.server.Server;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SchedulerManager {

    private final SyncScheduler sync;
    private final AsyncScheduler async;

    @Getter
    private final ExecutorService executor;

    @Getter
    private final Queue<Runnable> asyncCallables;

    public SchedulerManager() {
        sync = new SyncScheduler();
        async = new AsyncScheduler();
        int threads = Runtime.getRuntime().availableProcessors();
        executor = Executors.newFixedThreadPool(threads);
        asyncCallables = new ConcurrentLinkedQueue<>();
        Server.get().getLogger().info("Using " + threads + " threads for Scheduler");
    }

    public void addAsyncCallable(Runnable callable) {
        asyncCallables.add(callable);
    }

    public void cancel(SchedulerTask schedulerTask) {
        if (schedulerTask.isAsync()) {
            async.remove(schedulerTask.getTaskID());
        } else {
            sync.remove(schedulerTask.getTaskID());
        }
        schedulerTask.setCanceled(true);
    }

    public void call() {
        async.call();
        sync.call();
    }

    public void shutdown() {
        executor.shutdown();
    }


    public SyncScheduler sync() {
        return sync;
    }

    public AsyncScheduler async() {
        return async;
    }
}
