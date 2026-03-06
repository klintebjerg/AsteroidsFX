package io.donut.common;

/**
 * The central EventBus that maps events to subscribers.
 */
public final class EventBus {
    private static EventBus instance;

    private EventBus() {

    }

    /**
     * Get the Singleton instance of the EventBus.
     * @return the EventBus instance.
     */
    public static EventBus getInstance() {
        if(instance == null) {
            instance = new EventBus();
        }
        return instance;
    }
}
