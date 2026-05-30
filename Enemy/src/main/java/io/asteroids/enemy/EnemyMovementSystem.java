package io.asteroids.enemy;

import io.asteroids.common.*;
import io.asteroids.commonphysics.*;
import io.asteroids.commonplayer.PlayerComponent;

import java.util.List;

public class EnemyMovementSystem extends IteratingSystem implements ISystemSPI {

    private static final float SPEED = 110f;

    public EnemyMovementSystem() {
        setPriority(5);
    }

    @Override
    public void start(IWorld world) {
        world.addSystem(this);
    }

    @Override
    public void stop(IWorld world) {}

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PositionComponent.class, VelocityComponent.class, AngleComponent.class, EnemyComponent.class);
    }

    @Override
    public void processEntity(IWorld world, BaseEntity enemy, float deltaTime) {
        PositionComponent enemyPos = enemy.getComponent(PositionComponent.class);
        VelocityComponent vel      = enemy.getComponent(VelocityComponent.class);
        AngleComponent    angle    = enemy.getComponent(AngleComponent.class);

        BaseEntity target = findTarget(world);
        if (target == null) return;

        PositionComponent targetPos = target.getComponent(PositionComponent.class);
        float dx = targetPos.pos.x() - enemyPos.pos.x();
        float dy = targetPos.pos.y() - enemyPos.pos.y();
        float dist = (float) Math.sqrt(dx * dx + dy * dy);

        if (dist < 1f) return;

        vel.vel.x(dx / dist * SPEED);
        vel.vel.y(dy / dist * SPEED);
        angle.ang = (float) Math.toDegrees(Math.atan2(dy, dx));
    }

    private BaseEntity findTarget(IWorld world) {
        List<BaseEntity> players = world.getEntitiesWith(PlayerComponent.class);
        return players.isEmpty() ? null : players.get(0);
    }
}
