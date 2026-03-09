package io.donut.core;

import io.donut.common.*;
import java.util.*;

public class World implements IWorld{
    List<BaseEntity> entities;
    Set<BaseSystem> systems;
    EventBus eventBus;
    /**
     * The world constructor. It constructs the world
     */
    public World() {
        entities = new ArrayList<>();

        Comparator<BaseSystem> systemComparator =
                Comparator.comparingInt(BaseSystem::getPriority)
                        .thenComparing(system -> system.getClass().getName());

        systems = new TreeSet<>(systemComparator);
    }

    /**
     * Get all entities in the world
     * @return all entities
     */
    public List<BaseEntity> getEntities() {
        return new ArrayList<>(entities);
    }

    /**
     * Get a list of all entities with the componentType.
     * @param componentType the componentType that entities must include.
     * @return a list of entities which all have the componentType.
     */
    public <T extends BaseComponent> List<BaseEntity> getEntitiesWith(T componentType) {
        Set<BaseComponent> components = new HashSet<>();

        return entities;
    }

    /**
     * Adds a new system to the world
     * @param system defines the system implementation getting added to the world
     * @param <T> is a generic instance that extends from BaseSystem
     */
    public <T extends BaseSystem> void addSystem(T system) {
        if(system == null) {
            throw new IllegalArgumentException("System cannot be null");
        }
        systems.add(system);
    }

    /**
     * Adds a new entity to the world
     * @param entity is the new entity being added to the world
     * @param <T> is a generic instance that extends from BaseEntity
     */
    public <T extends BaseEntity> void addEntity(T entity) {
        entities.add(entity);
    }

    /**
     * Updates all systems inside the world
     * @param dt defines the time between each game frame
     */
    public void update(float dt) {
        for(BaseSystem system : systems) {
            system.update(dt);
        }
    }

    /**
     * Removes all entities that are null and removes their components
     */
    public void removeNullEntities() {
        entities.removeIf(Objects::isNull);
        for (BaseEntity entity : entities) {
            entity.removeNullComponents();
        }
    }

    /**
     * Gets the EventBus of the world
     * @return EventBus instance
     */
    public EventBus getEventBus() {
        return eventBus;
    }
}
