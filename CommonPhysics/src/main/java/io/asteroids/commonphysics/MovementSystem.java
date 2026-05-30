package io.asteroids.commonphysics;

import io.asteroids.common.*;

import java.util.List;

public class MovementSystem extends IteratingSystem implements ISystemSPI {

    @Override
    public void start(IWorld world) {
        world.addSystem(this);
    }

    @Override
    public void stop(IWorld world) {}

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PositionComponent.class, VelocityComponent.class);
    }

    @Override
    public void processEntity(IWorld world, BaseEntity entity, float deltaTime) {
        PositionComponent pos = entity.getComponent(PositionComponent.class);
        VelocityComponent vel = entity.getComponent(VelocityComponent.class);
        pos.pos.x(pos.pos.x() + vel.vel.x() * deltaTime);
        pos.pos.y(pos.pos.y() + vel.vel.y() * deltaTime);
    }
}
