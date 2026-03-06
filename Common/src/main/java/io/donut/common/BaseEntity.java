package io.donut.common;

import java.util.HashMap;
import java.util.Map;

/**
 * The BaseEntity class that every entity must derive from.
 */
public abstract class BaseEntity {

    private boolean isAlive = true;
    private final Map<Class<? extends BaseComponent>, BaseComponent> componentMap = new HashMap<>();

    /**
     * Add a component to the entity if a component of the same type is not already present.
     * @param component the component to add.
     * @return boolean indicating successful insert.
     */
    public <T extends BaseComponent> boolean addComponent(T component) {
        return componentMap.putIfAbsent(component.getClass(), component) != null;
    }

    /**
     * Finds the component of type T if present, otherwise null.
     * @param componentType the component type to get.
     * @return the instance of the component type or null.
     */
    public <T extends BaseComponent> T getComponent(Class<T> componentType) {
        if(!componentMap.containsKey(componentType)) return null;

        BaseComponent component = componentMap.get(componentType);
        if(!componentType.isAssignableFrom(component.getClass())) return null;

        return componentType.cast(component);
    }

    /**
     * Tells the entity if it is alive or not.
     * @param alive boolean indicating alive status.
     */
    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
