package io.asteroids.weapon;

import io.asteroids.common.*;
import io.asteroids.commonphysics.*;
import io.asteroids.commonplayer.PlayerComponent;
import io.asteroids.commonrender.PolygonComponent;

import java.util.List;

import static com.raylib.Raylib.KEY_SPACE;
import static com.raylib.Colors.YELLOW;

public class WeaponSystem extends IteratingSystem implements ISystemSPI {
    private static final float BULLET_SPEED = 600f;
    private static final float BULLET_LIFE  = 2f;
    private static final float COOLDOWN     = 0.25f;

    private boolean spaceDown = false;
    private float   cooldown  = 0f;

    @Override
    public void start(IWorld world) {
        spaceDown = false;
        cooldown  = 0f;
        world.addSystem(this);
        world.getEventBus().subscribe(KeyPressedEvent.class,  e -> { if (e.key == KEY_SPACE) spaceDown = true;  });
        world.getEventBus().subscribe(KeyReleasedEvent.class, e -> { if (e.key == KEY_SPACE) spaceDown = false; });
    }

    @Override
    public void stop(IWorld world) {}

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PositionComponent.class, AngleComponent.class, PlayerComponent.class);
    }

    @Override
    public void processEntity(IWorld world, BaseEntity entity, float deltaTime) {
        cooldown -= deltaTime;
        if (!spaceDown || cooldown > 0) return;
        cooldown = COOLDOWN;

        PositionComponent pos   = entity.getComponent(PositionComponent.class);
        AngleComponent    angle = entity.getComponent(AngleComponent.class);

        AsteroidsVector spawnPos = AsteroidsVector.fromAngleWithMag(angle.ang, 22f);
        spawnPos.add(pos.pos);

        BaseEntity bullet = new BaseEntity() {};

        PositionComponent bulletPos = new PositionComponent(new AsteroidsVector(spawnPos.x(), spawnPos.y()));
        bullet.addComponent(bulletPos);

        VelocityComponent bulletVel = new VelocityComponent();
        bulletVel.vel = AsteroidsVector.fromAngleWithMag(angle.ang, BULLET_SPEED);
        bullet.addComponent(bulletVel);

        AngleComponent bulletAngle = new AngleComponent();
        bulletAngle.ang = angle.ang;
        bullet.addComponent(bulletAngle);

        PolygonComponent bulletPoly = new PolygonComponent();
        bulletPoly.coordinates = new float[]{ 5,0, -3,-2, -3,2 };
        bulletPoly.fillColor = YELLOW;
        bullet.addComponent(bulletPoly);

        bullet.addComponent(new BulletComponent());
        bullet.addComponent(new LifetimeComponent(BULLET_LIFE));

        world.addEntity(bullet);
    }
}
