package io.donut.common;

/**
 * The BaseSystem class that every system must derive from.
 */
public abstract class BaseSystem {
    private int priority = 0;

    /**
     * Creates a new BaseSystem with the given priority.
     * @param priority the priority of the system. Lower values get processed earlier.
     */
    public BaseSystem(int priority) {
        this.priority = priority;
    }

    /**
     * Creates a new BaseSystem with a default priority of 0.
     */
    public BaseSystem() {
        this(0);
    }

    /**
     * Gets the priority of the system.
     * @return the priority of the system.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Sets the priority of the system. Lower values get processed earlier.
     * @param priority the priority value
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * The update method that will be called every frame. Override this method to implement the system's behavior.
     * @param dt the delta time since the last update call
     */
    public void update(float dt) {

    }
}
