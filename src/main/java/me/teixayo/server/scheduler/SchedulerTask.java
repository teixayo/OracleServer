package me.teixayo.server.scheduler;

import lombok.Getter;
import lombok.Setter;
import me.teixayo.server.Server;

import java.util.function.Consumer;

public class SchedulerTask implements Runnable {

    private final long limit;
    private final long later;
    private final long period;
    private final Consumer<SchedulerTask> runnable;
    @Getter
    private final int taskID;
    @Getter
    private final boolean async;
    @Setter
    @Getter
    private boolean isCanceled = false;
    private long lastTime;
    private long currentLimit = 0;

    public SchedulerTask(Consumer<SchedulerTask> runnable, long limit, long later, long period, int taskID, boolean async) {
        this.runnable = runnable;
        this.limit = limit;
        this.later = later * 50;
        this.period = period * 50;
        this.lastTime = System.currentTimeMillis();
        this.taskID = taskID;
        this.async = async;
    }

    public void run() {
        long elapsedTimeCheck = currentLimit == 0 ? later : period;

        if (System.currentTimeMillis() - lastTime >= elapsedTimeCheck) {


            if (async) {
                Server.get().getSchedulerManager().addAsyncCallable(() -> runnable.accept(SchedulerTask.this));
            } else {
                runnable.accept(this);
            }


            currentLimit++;

            if (currentLimit >= limit) {
                Server.get().getSchedulerManager().cancel(this);
                return;
            }
            lastTime = System.currentTimeMillis();
        }
    }

    public void cancel() {
        Server.get().getSchedulerManager().cancel(this);
    }

}
