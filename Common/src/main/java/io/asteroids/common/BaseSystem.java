package io.asteroids.common;

import java.util.List;

/**
 * The BaseSystem class that every system must derive from.
 */
public abstract class BaseSystem {

    private int priority;
    public boolean running = true;
    public BaseSystem(){
        this(0);
    }
    public BaseSystem(int priority){
        this.setPriority(priority);
    }

    /**
     * Defines the component signature for this system.
     * Only entities that possess all of the returned component types
     * will be processed by this system.
     *
     * @return a list of component types that an entity must have to be processed by this system.
     */
    public abstract List<Class<? extends BaseComponent>> getSignature();

    /**
     * Calls the update method on the system for a list of entities given a delta-time.
     * @param world the world implementation passed to the system.
     * @param entities the entities to update.
     * @param deltaTime the frame-time.
     */
    public abstract void update(IWorld world, List<BaseEntity> entities, float deltaTime);

    /**
     * Gets the priority of this system.
     * Systems with lower values are processed first.
     *
     * @return the priority value
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Sets the priority of this system.
     * Systems with lower values are processed first.
     *
     * @param priority the priority value to set
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }
}