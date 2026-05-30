package io.asteroids.commonphysics;

import io.asteroids.common.AsteroidsVector;
import io.asteroids.common.BaseEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovementSystemTest {

    private static final float DELTA = 0.001f;

    @Test
    void entityMovesAccordingToVelocityAndDeltaTime() {
        BaseEntity entity = new BaseEntity() {};
        entity.addComponent(new PositionComponent(new AsteroidsVector(0f, 0f)));
        VelocityComponent vel = new VelocityComponent();
        vel.vel = new AsteroidsVector(100f, 50f);
        entity.addComponent(vel);

        new MovementSystem().processEntity(null, entity, 0.5f);

        assertEquals(50f,  entity.getComponent(PositionComponent.class).pos.x(), DELTA);
        assertEquals(25f,  entity.getComponent(PositionComponent.class).pos.y(), DELTA);
    }

    @Test
    void zeroVelocityDoesNotMoveEntity() {
        BaseEntity entity = new BaseEntity() {};
        entity.addComponent(new PositionComponent(new AsteroidsVector(200f, 300f)));
        VelocityComponent vel = new VelocityComponent();
        vel.vel = new AsteroidsVector(0f, 0f);
        entity.addComponent(vel);

        new MovementSystem().processEntity(null, entity, 1f);

        assertEquals(200f, entity.getComponent(PositionComponent.class).pos.x(), DELTA);
        assertEquals(300f, entity.getComponent(PositionComponent.class).pos.y(), DELTA);
    }

    @Test
    void negativeVelocityMovesEntityBackwards() {
        BaseEntity entity = new BaseEntity() {};
        entity.addComponent(new PositionComponent(new AsteroidsVector(100f, 100f)));
        VelocityComponent vel = new VelocityComponent();
        vel.vel = new AsteroidsVector(-200f, -100f);
        entity.addComponent(vel);

        new MovementSystem().processEntity(null, entity, 1f);

        assertEquals(-100f, entity.getComponent(PositionComponent.class).pos.x(), DELTA);
        assertEquals(  0f,  entity.getComponent(PositionComponent.class).pos.y(), DELTA);
    }
}
