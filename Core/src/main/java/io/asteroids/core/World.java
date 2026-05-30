package io.asteroids.core;

import io.asteroids.common.*;
import java.util.*;

public class World implements IWorld{
    List<BaseEntity> entities;
    transient Set<BaseSystem> systems;
    transient IEventBus eventBus;
    private int score = 0;
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
    public <T extends BaseComponent> List<BaseEntity> getEntitiesWith(Class<T> componentClass) {
        List<BaseEntity> result = new ArrayList<>();
        for (BaseEntity entity : entities) {
            if (entity.isAlive() && entity.hasComponent(componentClass)) {
                result.add(entity);
            }
        }
        return result;
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

    public void setEventBus(IEventBus iEventBus) {
        this.eventBus = iEventBus;
    }

    /**
     * Updates all systems inside the world
     * @param dt defines the time between each game frame
     */
    public void update(float dt) {
        for(BaseSystem system : systems) {
            runSystem(system, dt);
        }
        entities.removeIf(e -> !e.isAlive());
    }

    private void runSystem(BaseSystem system, float deltaTime) {
        List<Class<? extends BaseComponent>> signature = system.getSignature();

        if (signature == null || signature.isEmpty()) {
            system.update(this, new ArrayList<>(), deltaTime);
            return;
        }

        List<BaseEntity> filteredEntities = new ArrayList<>();
        for (BaseEntity entity : getEntities()) {
            if (entity.hasComponent(signature)) filteredEntities.add(entity);
        }

        system.update(this, filteredEntities, deltaTime);
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
     * Gets the EventBus implementation of the world
     * @return IEventBus
     */
    public IEventBus getEventBus() {
        return eventBus;
    }

    @Override
    public void addScore(int points) {
        score += points;
    }

    @Override
    public int getScore() {
        return score;
    }
}
