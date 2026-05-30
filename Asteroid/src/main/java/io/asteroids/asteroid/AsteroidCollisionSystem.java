package io.asteroids.asteroid;

import io.asteroids.common.*;
import io.asteroids.commonphysics.BulletComponent;
import io.asteroids.commonphysics.PositionComponent;
import io.asteroids.commonplayer.PlayerComponent;

import java.util.List;

public class AsteroidCollisionSystem extends IteratingSystem implements ISystemSPI {

    public AsteroidCollisionSystem() {
        setPriority(10);
    }

    @Override
    public void start(IWorld world) {
        world.addSystem(this);
    }

    @Override
    public void stop(IWorld world) {}

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PositionComponent.class, AsteroidComponent.class);
    }

    private static final float PLAYER_RADIUS = 12f;

    @Override
    public void processEntity(IWorld world, BaseEntity asteroid, float deltaTime) {
        PositionComponent asteroidPos  = asteroid.getComponent(PositionComponent.class);
        AsteroidComponent asteroidComp = asteroid.getComponent(AsteroidComponent.class);
        float asteroidRadius = AsteroidFactory.radiusFor(asteroidComp.size);

        for (BaseEntity bullet : world.getEntitiesWith(BulletComponent.class)) {
            if (!bullet.isAlive()) continue;
            if (bullet.getComponent(BulletComponent.class).hostile) continue;

            PositionComponent bulletPos = bullet.getComponent(PositionComponent.class);
            float dx     = asteroidPos.pos.x() - bulletPos.pos.x();
            float dy     = asteroidPos.pos.y() - bulletPos.pos.y();
            float radSum = asteroidRadius + 5f;

            if (dx * dx + dy * dy < radSum * radSum) {
                bullet.setAlive(false);
                asteroid.setAlive(false);
                world.addScore(1);
                spawnChildren(world, asteroidComp.size, asteroidPos);
                if (world.getEntitiesWith(AsteroidComponent.class).isEmpty()) {
                    world.getEventBus().publish(new AllAsteroidsDestroyedEvent());
                }
                return;
            }
        }

        for (BaseEntity other : world.getEntitiesWith(PositionComponent.class)) {
            if (other.hasComponent(AsteroidComponent.class)) continue;
            if (other.hasComponent(BulletComponent.class)) continue;
            if (!other.isAlive()) continue;

            PositionComponent otherPos = other.getComponent(PositionComponent.class);
            float dx     = asteroidPos.pos.x() - otherPos.pos.x();
            float dy     = asteroidPos.pos.y() - otherPos.pos.y();
            float radSum = asteroidRadius + PLAYER_RADIUS;

            if (dx * dx + dy * dy < radSum * radSum) {
                other.setAlive(false);
                if (other.hasComponent(PlayerComponent.class)) {
                    world.getEventBus().publish(new ShipDestroyedEvent());
                }
            }
        }
    }

    private void spawnChildren(IWorld world, AsteroidComponent.Size parentSize,
                               PositionComponent pos) {
        if (parentSize == AsteroidComponent.Size.SMALL) return;

        AsteroidComponent.Size childSize = (parentSize == AsteroidComponent.Size.LARGE)
                ? AsteroidComponent.Size.MEDIUM
                : AsteroidComponent.Size.SMALL;

        float speed = (parentSize == AsteroidComponent.Size.LARGE) ? 90f : 150f;

        AsteroidFactory.spawnAsteroid(world, pos.pos, childSize,
                (float) (Math.random() * 360), speed);
        AsteroidFactory.spawnAsteroid(world, pos.pos, childSize,
                (float) (Math.random() * 360), speed);
    }
}
