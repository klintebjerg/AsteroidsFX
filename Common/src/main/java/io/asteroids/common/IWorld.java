package io.asteroids.common;

import java.util.List;

public interface IWorld {
    <T extends BaseComponent> List<BaseEntity> getEntitiesWith(Class<T> componentClass);

    <T extends BaseSystem> void addSystem(T system);

    <T extends BaseEntity> void addEntity(T entity);

    void update(float dt);

    void setEventBus(IEventBus eventBus);

    IEventBus getEventBus();

    void removeNullEntities();

    void addScore(int points);

    int getScore();
}
