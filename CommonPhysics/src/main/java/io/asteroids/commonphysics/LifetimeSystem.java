package io.asteroids.commonphysics;

import io.asteroids.common.*;

import java.util.List;

public class LifetimeSystem extends IteratingSystem implements ISystemSPI {

    public LifetimeSystem() {
        setPriority(20);
    }

    @Override
    public void start(IWorld world) {
        world.addSystem(this);
    }

    @Override
    public void stop(IWorld world) {}

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(LifetimeComponent.class);
    }

    @Override
    public void processEntity(IWorld world, BaseEntity entity, float deltaTime) {
        LifetimeComponent lifetime = entity.getComponent(LifetimeComponent.class);
        lifetime.remainingLife -= deltaTime;
        if (lifetime.remainingLife <= 0) {
            entity.setAlive(false);
        }
    }
}
