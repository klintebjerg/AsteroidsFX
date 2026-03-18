package io.asteroids.player;

import io.asteroids.common.*;
import io.asteroids.commonphysics.PositionComponent;
import io.asteroids.commonphysics.VelocityComponent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.raylib.Raylib.*;

public class PlayerMovementSystem extends IteratingSystem implements ISystemSPI {
    AsteroidsVector inputVelocity = new AsteroidsVector(0, 0);
    Set<Integer> keysPressed = new HashSet<>();

    @Override
    public void start(IWorld world) {
        world.addSystem(this);
        world.getEventBus().subscribe(KeyPressedEvent.class, this::handleKeyPressed);
        world.getEventBus().subscribe(KeyReleasedEvent.class, this::handleKeyReleased);
    }

    @Override
    public void stop(IWorld world) {
    }

    private void handleKeyPressed(KeyPressedEvent keyPressedEvent) {
        keysPressed.add(keyPressedEvent.key);
    }

    private void handleKeyReleased(KeyReleasedEvent keyReleasedEvent) {
        keysPressed.remove(keyReleasedEvent.key);
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PositionComponent.class, VelocityComponent.class);
    }

    @Override
    public void processEntity(IWorld world, BaseEntity entity, float deltaTime) {
        VelocityComponent velocityComponent = entity.getComponent(VelocityComponent.class);
        float dy = 0, dx = 0;
        float speed = 150;

        if (keysPressed.contains(KEY_RIGHT) || keysPressed.contains(KEY_D)) dx += 1;
        if (keysPressed.contains(KEY_LEFT)  || keysPressed.contains(KEY_A)) dx -= 1;
        if (keysPressed.contains(KEY_DOWN)  || keysPressed.contains(KEY_S)) dy += 1;
        if (keysPressed.contains(KEY_UP)    || keysPressed.contains(KEY_W)) dy -= 1;

        inputVelocity.x(dx);
        inputVelocity.y(dy);
        inputVelocity.normalize();
        inputVelocity.mult(speed);

        velocityComponent.vel = inputVelocity;
    }
}
