package io.asteroids.core;

import io.asteroids.common.BaseEvent;
import io.asteroids.common.IEventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public final class EventBus implements IEventBus {
    private final Map<Class<? extends BaseEvent>, List<Consumer<? extends BaseEvent>>> eventHandlerMap = new HashMap<>();

    @Override
    public <T extends BaseEvent> void subscribe(Class<T> eventType, Consumer<T> handler) {
        eventHandlerMap.computeIfAbsent(eventType, k -> new ArrayList<>()).add(handler);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void publish(BaseEvent event) {
        List<Consumer<? extends BaseEvent>> handlers = eventHandlerMap.get(event.getClass());
        if (handlers == null) return;
        for (Consumer handler : handlers) {
            handler.accept(event);
        }
    }
}
