package me.teixayo.server.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class EventManager {

    //method with owner
    private final ConcurrentHashMap<Object, List<EventMethod>> methodsPerObject = new ConcurrentHashMap<>();

    //method with type
    private final ConcurrentHashMap<Class<?>, List<EventMethod>> methodPerType = new ConcurrentHashMap<>();


    public void register(Object object) {
        for (Method method : object.getClass().getMethods()) {
            EventHandler annotation = method.getAnnotation(EventHandler.class);

            if (annotation == null) continue;

            Class<?> parameterType = method.getParameterTypes()[0];

            EventMethod e = new EventMethod(parameterType, object, method);

            List<EventMethod> eventMethods = methodsPerObject.get(object);
            if (eventMethods == null) {
                List<EventMethod> newList = new ArrayList<>();
                newList.add(e);
                methodsPerObject.put(object, newList);
            } else {
                eventMethods.add(e);
            }

            List<EventMethod> eventMethod = methodPerType.get(parameterType);
            if (eventMethod == null) {
                List<EventMethod> newList = new ArrayList<>();
                newList.add(e);
                methodPerType.put(parameterType, newList);
            } else {
                eventMethod.add(e);
            }
        }
    }

    public void unregister(Object object) {
        List<EventMethod> eventMethods = methodsPerObject.get(object);
        if (eventMethods == null) return;
        methodsPerObject.remove(object);

        int size = eventMethods.size();
        int i = 0;

        for (Class<?> aClass : methodPerType.keySet()) {
            if (size == i) return;
            List<EventMethod> eventMethodHashMap = methodPerType.get(aClass);

            for (EventMethod eventMethod : eventMethods) {
                eventMethodHashMap.remove(eventMethod);
                i++;
            }

        }
    }

    public void call(Object object) {
        List<EventMethod> eventMethods = methodPerType.get(object.getClass());
        if (eventMethods == null) return;
        if (eventMethods.isEmpty()) {
            methodPerType.remove(object.getClass());
        }

        for (EventMethod eventMethod : eventMethods) {
            try {
                eventMethod.method().invoke(eventMethod.owner(), object);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}