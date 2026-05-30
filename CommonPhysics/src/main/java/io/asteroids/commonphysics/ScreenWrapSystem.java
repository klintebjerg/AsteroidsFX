package io.asteroids.commonphysics;

import io.asteroids.common.*;

import java.util.List;

import static com.raylib.Raylib.GetScreenHeight;
import static com.raylib.Raylib.GetScreenWidth;

public class ScreenWrapSystem extends IteratingSystem implements ISystemSPI {

    public ScreenWrapSystem() {
        setPriority(15);
    }

    @Override
    public void start(IWorld world) {
        world.addSystem(this);
    }

    @Override
    public void stop(IWorld world) {}

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PositionComponent.class);
    }

    @Override
    public void processEntity(IWorld world, BaseEntity entity, float deltaTime) {
        PositionComponent pos = entity.getComponent(PositionComponent.class);
        int w = GetScreenWidth();
        int h = GetScreenHeight();

        float x = pos.pos.x(), y = pos.pos.y();
        boolean offLeft   = x < 0;
        boolean offRight  = x > w;
        boolean offTop    = y < 0;
        boolean offBottom = y > h;

        if (!offLeft && !offRight && !offTop && !offBottom) return;

        if (entity.hasComponent(BulletComponent.class)) {
            entity.setAlive(false);
            return;
        }

        VelocityComponent vel = entity.getComponent(VelocityComponent.class);
        if (vel == null) return;

        final float DAMPING = 0.4f;
        if (offLeft)   { pos.pos.x(0); vel.vel.x( Math.abs(vel.vel.x()) * DAMPING); }
        if (offRight)  { pos.pos.x(w); vel.vel.x(-Math.abs(vel.vel.x()) * DAMPING); }
        if (offTop)    { pos.pos.y(0); vel.vel.y( Math.abs(vel.vel.y()) * DAMPING); }
        if (offBottom) { pos.pos.y(h); vel.vel.y(-Math.abs(vel.vel.y()) * DAMPING); }
    }
}
