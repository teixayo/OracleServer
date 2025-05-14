package me.teixayo.server.scheduler;

import lombok.Getter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class SyncScheduler {

    private static int TASKS_ID = 0;
    @Getter
    private final ConcurrentHashMap<Integer, SchedulerTask> tasks = new ConcurrentHashMap<>();

    public void remove(int taskID) {
        tasks.remove(taskID);
    }

    public void call() {
        for (SchedulerTask task : tasks.values()) {
            task.run();
        }
    }

    public SchedulerTask run(Consumer<SchedulerTask> runnable) {
        SchedulerTask task = new SchedulerTask(runnable, 1, 0, 0, TASKS_ID, false);
        tasks.put(TASKS_ID++, task);
        return task;
    }


    public SchedulerTask runLater(Consumer<SchedulerTask> runnable, long later) {
        SchedulerTask task = new SchedulerTask(runnable, 1, later, 0, TASKS_ID, false);
        tasks.put(TASKS_ID++, task);
        return task;
    }


    public SchedulerTask runTimer(Consumer<SchedulerTask> runnable, long later, long period) {
        SchedulerTask task = new SchedulerTask(runnable, Integer.MAX_VALUE, later, period, TASKS_ID, false);
        tasks.put(TASKS_ID++, task);
        return task;
    }


    public SchedulerTask runTimer(Consumer<SchedulerTask> runnable, long later, long period, long limit) {
        SchedulerTask task = new SchedulerTask(runnable, limit, later, period, TASKS_ID, false);
        tasks.put(TASKS_ID++, task);
        return task;
    }
}
