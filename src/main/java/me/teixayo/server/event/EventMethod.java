package me.teixayo.server.event;

import java.lang.reflect.Method;

public class EventMethod {

    private final Class<?> type;
    private final Object owner;
    private final Method method;

    public EventMethod(Class<?> type, Object owner, Method method) {
        this.type = type;
        this.owner = owner;
        this.method = method;
    }

    public Class<?> type() {
        return type;
    }

    public Object owner() {
        return owner;
    }

    public Method method() {
        return method;
    }
}
