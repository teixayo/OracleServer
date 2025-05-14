package me.teixayo.server.event;

import lombok.Getter;

import java.lang.reflect.Method;

public class EventMethod {

    @Getter
    private final Class<?> type;
    @Getter
    private final Object owner;
    @Getter
    private final Method method;

    public EventMethod(Class<?> type, Object owner, Method method) {
        this.type = type;
        this.owner = owner;
        this.method = method;
    }
}
