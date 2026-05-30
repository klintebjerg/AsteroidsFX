package io.asteroids.asteroid;

import io.asteroids.common.AsteroidsVector;
import io.asteroids.common.BaseComponent;
import io.asteroids.common.BaseEntity;
import io.asteroids.common.ISystemSPI;
import io.asteroids.common.IWorld;
import io.asteroids.common.BaseSystem;

import java.util.List;

public class AsteroidSpawnerSystem extends BaseSystem implements ISystemSPI {

    private static final int   COUNT        = 5;
    private static final float SPAWN_RADIUS = 280f;
    private static final float MIN_SPEED    = 50f;
    private static final float MAX_SPEED    = 80f;

    @Override
    public void start(IWorld world) {
        world.addSystem(this);
        for (int i = 0; i < COUNT; i++) {
            // Evenly spread around player (400,300) at SPAWN_RADIUS
            float spawnAngle = i * (360f / COUNT);
            float x = 400 + (float) Math.cos(Math.toRadians(spawnAngle)) * SPAWN_RADIUS;
            float y = 300 + (float) Math.sin(Math.toRadians(spawnAngle)) * SPAWN_RADIUS;
            float velAngle = (float) (Math.random() * 360);
            float speed = MIN_SPEED + (float) (Math.random() * (MAX_SPEED - MIN_SPEED));
            AsteroidFactory.spawnAsteroid(world, new AsteroidsVector(x, y),
                    AsteroidComponent.Size.LARGE, velAngle, speed);
        }
    }

    @Override
    public void stop(IWorld world) {}

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of();
    }

    @Override
    public void update(IWorld world, List<BaseEntity> entities, float deltaTime) {}
}
