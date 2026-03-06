package io.donut.common;

public final class EventBus {
    private static EventBus instance;

    private EventBus() {

    }

    public static EventBus getInstance() {
        if(instance == null) {
            instance = new EventBus();
        }
        return instance;
    }
}
