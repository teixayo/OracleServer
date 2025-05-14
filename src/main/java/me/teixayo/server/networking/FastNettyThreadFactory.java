package me.teixayo.server.networking;

import io.netty.util.concurrent.FastThreadLocalThread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class FastNettyThreadFactory implements ThreadFactory {

    private final AtomicInteger threadNumber = new AtomicInteger();
    private final String nameFormat;

    public FastNettyThreadFactory(String nameFormat) {
        this.nameFormat = nameFormat;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        String name = String.format(nameFormat, threadNumber.getAndIncrement());
        return new FastThreadLocalThread(runnable, name);
    }
}
