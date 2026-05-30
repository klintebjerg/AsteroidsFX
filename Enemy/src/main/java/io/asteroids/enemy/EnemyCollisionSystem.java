package io.asteroids.enemy;

import io.asteroids.common.*;
import io.asteroids.commonphysics.*;
import io.asteroids.commonplayer.PlayerComponent;

import java.util.List;

public class EnemyCollisionSystem extends IteratingSystem implements ISystemSPI {

    private static final float ENEMY_RADIUS = 14f;

    public EnemyCollisionSystem() {
        setPriority(11);
    }

    @Override
    public void start(IWorld world) {
        world.addSystem(this);
    }

    @Override
    public void stop(IWorld world) {}

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PositionComponent.class, EnemyComponent.class);
    }

    @Override
    public void update(IWorld world, List<BaseEntity> enemies, float deltaTime) {
        super.update(world, enemies, deltaTime);

        for (BaseEntity bullet : world.getEntitiesWith(BulletComponent.class)) {
            if (!bullet.isAlive()) continue;
            if (!bullet.getComponent(BulletComponent.class).hostile) continue;

            PositionComponent bulletPos = bullet.getComponent(PositionComponent.class);

            for (BaseEntity entity : world.getEntitiesWith(PlayerComponent.class)) {
                if (!entity.isAlive()) continue;

                PositionComponent entityPos = entity.getComponent(PositionComponent.class);
                float dx = entityPos.pos.x() - bulletPos.pos.x();
                float dy = entityPos.pos.y() - bulletPos.pos.y();

                if (dx * dx + dy * dy < 12f * 12f) {
                    bullet.setAlive(false);
                    entity.setAlive(false);
                    world.getEventBus().publish(new ShipDestroyedEvent());
                    break;
                }
            }
        }
    }

    @Override
    public void processEntity(IWorld world, BaseEntity enemy, float deltaTime) {
        PositionComponent enemyPos = enemy.getComponent(PositionComponent.class);

        for (BaseEntity bullet : world.getEntitiesWith(BulletComponent.class)) {
            if (!bullet.isAlive()) continue;
            if (bullet.getComponent(BulletComponent.class).hostile) continue;

            PositionComponent bulletPos = bullet.getComponent(PositionComponent.class);
            float dx     = enemyPos.pos.x() - bulletPos.pos.x();
            float dy     = enemyPos.pos.y() - bulletPos.pos.y();
            float radSum = ENEMY_RADIUS + 5f;

            if (dx * dx + dy * dy < radSum * radSum) {
                bullet.setAlive(false);
                enemy.setAlive(false);
                world.addScore(3);
                return;
            }
        }
    }
}
