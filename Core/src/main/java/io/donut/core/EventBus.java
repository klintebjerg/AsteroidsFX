package io.donut.core;

import io.donut.common.BaseEvent;
import io.donut.common.IEventBus;
import io.donut.common.ISubscriber;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public final class EventBus implements IEventBus{
    Map<Class<? extends BaseEvent>, HashSet<ISubscriber>> eventSubscriberMap = new HashMap<>();

    @Override
    public void subscribe(ISubscriber subscriber, Class<? extends BaseEvent> eventType) {
        eventSubscriberMap.putIfAbsent(eventType, new HashSet<>());
        eventSubscriberMap.get(eventType).add(subscriber);
    }

    @Override
    public void publish(BaseEvent event) {
        if(!eventSubscriberMap.containsKey(event.getClass())) return;

        for(ISubscriber subscriber : eventSubscriberMap.get(event.getClass())) {
            subscriber.OnEvent();
        }
    }
}
