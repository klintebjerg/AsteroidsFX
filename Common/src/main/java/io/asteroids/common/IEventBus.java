package io.asteroids.common;

import java.util.function.Consumer;

public interface IEventBus {
    <T extends BaseEvent> void subscribe(Class<T> eventType, Consumer<T> handler);

    void publish(BaseEvent event);
}
