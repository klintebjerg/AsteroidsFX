package io.asteroids.player;

import io.asteroids.common.*;
import io.asteroids.commonphysics.AngleComponent;
import io.asteroids.commonphysics.VelocityComponent;
import io.asteroids.commonplayer.PlayerComponent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.raylib.Raylib.*;

public class PlayerMovementSystem extends IteratingSystem implements ISystemSPI {
    private static final float ROTATION_SPEED   = 200f;
    private static final float THRUST_FORCE     = 250f;
    private static final float MAX_SPEED        = 500f;
    private static final float DECELERATION     = 300f;

    private final Set<Integer> keysDown = new HashSet<>();

    @Override
    public void start(IWorld world) {
        keysDown.clear();
        world.addSystem(this);
        world.getEventBus().subscribe(KeyPressedEvent.class, e -> keysDown.add(e.key));
        world.getEventBus().subscribe(KeyReleasedEvent.class, e -> keysDown.remove(e.key));
    }

    @Override
    public void stop(IWorld world) {}

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(VelocityComponent.class, AngleComponent.class, PlayerComponent.class);
    }

    @Override
    public void processEntity(IWorld world, BaseEntity entity, float deltaTime) {
        VelocityComponent vel = entity.getComponent(VelocityComponent.class);
        AngleComponent angle = entity.getComponent(AngleComponent.class);

        if (keysDown.contains(KEY_LEFT) || keysDown.contains(KEY_A))
            angle.ang -= ROTATION_SPEED * deltaTime;
        if (keysDown.contains(KEY_RIGHT) || keysDown.contains(KEY_D))
            angle.ang += ROTATION_SPEED * deltaTime;

        if (keysDown.contains(KEY_UP) || keysDown.contains(KEY_W)) {
            AsteroidsVector thrust = AsteroidsVector.fromAngleWithMag(angle.ang, THRUST_FORCE * deltaTime);
            vel.vel.add(thrust);
            if (vel.vel.mag() > MAX_SPEED) vel.vel.setMag(MAX_SPEED);
        }

        if (keysDown.contains(KEY_DOWN) || keysDown.contains(KEY_S)) {
            float speed = vel.vel.mag();
            if (speed > 0) {
                float newSpeed = speed - DECELERATION * deltaTime;
                if (newSpeed <= 0) {
                    vel.vel.x(0);
                    vel.vel.y(0);
                } else {
                    vel.vel.setMag(newSpeed);
                }
            }
        }
    }
}
