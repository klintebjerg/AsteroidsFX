package io.donut.common;

public interface IEventBus {

    /**
     * Subscribe to an eventType.
     * @param subscriber defines the subscriber, subscribing to a specific event in the eventbus
     * @param eventType defines the event that the subscriber wants
     */
    void subscribe(ISubscriber subscriber, Class<? extends BaseEvent> eventType);

    /**
     * Publish an event to the eventbus, notifying all subscribers of said eventType
     * @param event the event to publish
     */
    void publish(BaseEvent event);
}
