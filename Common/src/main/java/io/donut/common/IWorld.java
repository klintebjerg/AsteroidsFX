package io.donut.common;


import java.util.List;

public interface IWorld {
    public <T extends BaseComponent> List<BaseEntity> getEntitiesWith(T componentType);

    public <T extends BaseSystem> void addSystem(T system);

    public <T extends BaseEntity> void addEntity(T entity);
}

