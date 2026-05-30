package io.asteroids.enemy;

import io.asteroids.common.*;
import io.asteroids.commonphysics.*;
import io.asteroids.commonplayer.PlayerComponent;
import io.asteroids.commonrender.PolygonComponent;

import java.util.List;

import static com.raylib.Colors.ORANGE;

public class EnemyShootingSystem extends IteratingSystem implements ISystemSPI {

    private static final float SHOOT_INTERVAL = 2.5f;
    private static final float BULLET_SPEED   = 400f;
    private static final float BULLET_LIFE    = 2.5f;

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
    public void processEntity(IWorld world, BaseEntity enemy, float deltaTime) {
        EnemyComponent    enemyComp = enemy.getComponent(EnemyComponent.class);
        PositionComponent enemyPos  = enemy.getComponent(PositionComponent.class);

        enemyComp.shootCooldown -= deltaTime;
        if (enemyComp.shootCooldown > 0) return;
        enemyComp.shootCooldown = SHOOT_INTERVAL;

        BaseEntity target = findTarget(world);
        if (target == null) return;

        PositionComponent targetPos = target.getComponent(PositionComponent.class);
        float dx = targetPos.pos.x() - enemyPos.pos.x();
        float dy = targetPos.pos.y() - enemyPos.pos.y();
        float dist = (float) Math.sqrt(dx * dx + dy * dy);
        if (dist < 1f) return;

        BaseEntity bullet = new BaseEntity() {};

        bullet.addComponent(new PositionComponent(
                new AsteroidsVector(enemyPos.pos.x(), enemyPos.pos.y())));

        VelocityComponent bulletVel = new VelocityComponent();
        bulletVel.vel = new AsteroidsVector(dx / dist * BULLET_SPEED, dy / dist * BULLET_SPEED);
        bullet.addComponent(bulletVel);

        AngleComponent bulletAngle = new AngleComponent();
        bulletAngle.ang = (float) Math.toDegrees(Math.atan2(dy, dx));
        bullet.addComponent(bulletAngle);

        PolygonComponent poly = new PolygonComponent();
        poly.coordinates = new float[]{ 5,0, -3,-2, -3,2 };
        poly.fillColor = ORANGE;
        bullet.addComponent(poly);

        BulletComponent bc = new BulletComponent();
        bc.hostile = true;
        bullet.addComponent(bc);

        bullet.addComponent(new LifetimeComponent(BULLET_LIFE));

        world.addEntity(bullet);
    }

    private BaseEntity findTarget(IWorld world) {
        List<BaseEntity> players = world.getEntitiesWith(PlayerComponent.class);
        return players.isEmpty() ? null : players.get(0);
    }
}
