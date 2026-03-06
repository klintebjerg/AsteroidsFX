package io.donut.common;

import java.util.HashMap;
import java.util.Map;

/**
 * The BaseEntity class that every entity must derive from.
 */
public abstract class BaseEntity {

    private boolean isAlive = true;

    /// Map from the class of a component to the instance of the component.
    /// <br>
    /// This ensures each entity only can have one of each type of component.
    private final Map<Class<? extends BaseComponent>, BaseComponent> componentMap = new HashMap<>();

    /**
     * Add a component to the entity if a component of the same type is not already present.
     * @param component the component to add.
     * @return boolean indicating successful insert.
     */
    public <T extends BaseComponent> boolean addComponent(T component) {
        // only add if not already present, never overwrite
        return componentMap.putIfAbsent(component.getClass(), component) != null;
    }

    /**
     * Finds the component of type T if present, otherwise null.
     * @param componentType the component type to get.
     * @return the instance of the component type or null.
     */
    public <T extends BaseComponent> T getComponent(Class<T> componentType) {

        // if type not in map, return
        if(!componentMap.containsKey(componentType)) return null;

        BaseComponent component = componentMap.get(componentType);

        // component.getClass() returns the runtime class of the component, e.g. PositionComponent
        // check if the class of component is equal to (or a subclass of) the componentType, if not, return
        if(!componentType.isAssignableFrom(component.getClass())) return null;

        // cast the component to the type the caller requested
        return componentType.cast(component);
    }

    /**
     * Checks if the entity has a component of the given type.
     * @param componentType the component type to check for.
     * @return true if the entity has a component of type, otherwise false
     */
    public <T extends BaseComponent> boolean hasComponent(Class<T> componentType){
        return componentMap.containsKey(componentType);
    }

    /**
     * Tells the entity if it is alive or not.
     * @param alive boolean indicating alive status.
     */
    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
