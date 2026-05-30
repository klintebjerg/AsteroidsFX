package io.asteroids.enemy;

import io.asteroids.common.*;
import io.asteroids.commonphysics.*;
import io.asteroids.commonrender.PolygonComponent;

import java.util.List;

import static com.raylib.Colors.RED;

public class EnemySpawnerSystem extends BaseSystem implements ISystemSPI {

    private static final float RESPAWN_DELAY = 4f;
    private float respawnTimer = 0f;

    @Override
    public void start(IWorld world) {
        world.addSystem(this);
        spawnEnemy(world);
    }

    @Override
    public void stop(IWorld world) {}

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of();
    }

    @Override
    public void update(IWorld world, List<BaseEntity> entities, float deltaTime) {
        if (!world.getEntitiesWith(EnemyComponent.class).isEmpty()) {
            respawnTimer = RESPAWN_DELAY;
            return;
        }
        respawnTimer -= deltaTime;
        if (respawnTimer <= 0) {
            spawnEnemy(world);
        }
    }

    private static final float MIN_SPAWN_CLEARANCE = 80f;
    private static final int   MAX_SPAWN_ATTEMPTS  = 20;

    private void spawnEnemy(IWorld world) {
        BaseEntity enemy = new BaseEntity() {};

        float x = 0, y = 0;
        for (int attempt = 0; attempt < MAX_SPAWN_ATTEMPTS; attempt++) {
            int edge = (int) (Math.random() * 4);
            switch (edge) {
                case 0  -> { x = 30;  y = (float) (Math.random() * 600); }
                case 1  -> { x = 770; y = (float) (Math.random() * 600); }
                case 2  -> { x = (float) (Math.random() * 800); y = 30;  }
                default -> { x = (float) (Math.random() * 800); y = 570; }
            }
            if (isClear(world, x, y)) break;
        }

        PositionComponent pos = new PositionComponent(new AsteroidsVector(x, y));
        enemy.addComponent(pos);

        VelocityComponent vel = new VelocityComponent();
        vel.vel = new AsteroidsVector(0, 0);
        enemy.addComponent(vel);

        AngleComponent angle = new AngleComponent();
        angle.ang = 0;
        enemy.addComponent(angle);

        PolygonComponent poly = new PolygonComponent();
        poly.coordinates = new float[]{ 0,-16, 14,0, 0,16, -14,0 };
        poly.fillColor = RED;
        enemy.addComponent(poly);

        enemy.addComponent(new EnemyComponent());

        world.addEntity(enemy);
    }

    private boolean isClear(IWorld world, float x, float y) {
        for (BaseEntity e : world.getEntitiesWith(PositionComponent.class)) {
            if (e.hasComponent(BulletComponent.class)) continue;
            PositionComponent pos = e.getComponent(PositionComponent.class);
            float dx = pos.pos.x() - x;
            float dy = pos.pos.y() - y;
            if (dx * dx + dy * dy < MIN_SPAWN_CLEARANCE * MIN_SPAWN_CLEARANCE) return false;
        }
        return true;
    }
}
