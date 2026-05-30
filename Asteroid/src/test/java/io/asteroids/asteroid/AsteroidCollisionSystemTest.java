package io.asteroids.asteroid;

import io.asteroids.common.*;
import io.asteroids.commonphysics.*;
import io.asteroids.commonplayer.PlayerComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class AsteroidCollisionSystemTest {

    private StubWorld world;

    @BeforeEach
    void setUp() {
        world = new StubWorld();
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private BaseEntity makeAsteroid(float x, float y, AsteroidComponent.Size size) {
        BaseEntity asteroid = new BaseEntity() {};
        asteroid.addComponent(new PositionComponent(new AsteroidsVector(x, y)));
        asteroid.addComponent(new AsteroidComponent(size));
        world.addEntity(asteroid);
        return asteroid;
    }

    private BaseEntity makeBullet(float x, float y, boolean hostile) {
        BaseEntity bullet = new BaseEntity() {};
        bullet.addComponent(new PositionComponent(new AsteroidsVector(x, y)));
        BulletComponent bc = new BulletComponent();
        bc.hostile = hostile;
        bullet.addComponent(bc);
        world.addEntity(bullet);
        return bullet;
    }

    // ── tests ─────────────────────────────────────────────────────────────────

    @Test
    void playerBulletDirectlyOnAsteroidKillsBoth() {
        BaseEntity asteroid = makeAsteroid(100f, 100f, AsteroidComponent.Size.SMALL);
        BaseEntity bullet   = makeBullet(100f, 100f, false);

        new AsteroidCollisionSystem().processEntity(world, asteroid, 0.016f);

        assertFalse(asteroid.isAlive(), "Asteroid should be dead");
        assertFalse(bullet.isAlive(),   "Bullet should be dead");
    }

    @Test
    void playerBulletFarFromAsteroidLeavesEntitiesAlive() {
        BaseEntity asteroid = makeAsteroid(100f, 100f, AsteroidComponent.Size.SMALL);
        BaseEntity bullet   = makeBullet(500f, 500f, false);

        new AsteroidCollisionSystem().processEntity(world, asteroid, 0.016f);

        assertTrue(asteroid.isAlive(), "Asteroid should still be alive");
        assertTrue(bullet.isAlive(),   "Bullet should still be alive");
    }

    @Test
    void largeAsteroidSpawnsTwoChildrenOnHit() {
        makeAsteroid(100f, 100f, AsteroidComponent.Size.LARGE);
        makeBullet(100f, 100f, false);

        long before = world.entities.size();
        BaseEntity asteroid = world.entities.get(0);
        new AsteroidCollisionSystem().processEntity(world, asteroid, 0.016f);

        assertEquals(before + 2, world.entities.size(), "Two medium children should spawn");
    }

    @Test
    void smallAsteroidSpawnsNoChildrenOnHit() {
        BaseEntity asteroid = makeAsteroid(100f, 100f, AsteroidComponent.Size.SMALL);
        makeBullet(100f, 100f, false);

        long before = world.entities.size();
        new AsteroidCollisionSystem().processEntity(world, asteroid, 0.016f);

        assertEquals(before, world.entities.size(), "No children should spawn from a small asteroid");
    }

    @Test
    void hostileBulletDoesNotKillAsteroid() {
        BaseEntity asteroid = makeAsteroid(100f, 100f, AsteroidComponent.Size.SMALL);
        makeBullet(100f, 100f, true);

        new AsteroidCollisionSystem().processEntity(world, asteroid, 0.016f);

        assertTrue(asteroid.isAlive(), "Asteroid should survive a hostile (enemy) bullet");
    }

    // ── stub ──────────────────────────────────────────────────────────────────

    static class StubWorld implements IWorld {
        final List<BaseEntity> entities = new ArrayList<>();

        @Override
        public <T extends BaseComponent> List<BaseEntity> getEntitiesWith(Class<T> c) {
            return entities.stream()
                    .filter(e -> e.isAlive() && e.hasComponent(c))
                    .collect(Collectors.toList());
        }

        @Override public <T extends BaseSystem> void addSystem(T s) {}
        @Override public <T extends BaseEntity> void addEntity(T e) { entities.add(e); }
        @Override public void update(float dt) {}
        @Override public void setEventBus(IEventBus b) {}
        @Override public void removeNullEntities() {}
        @Override public void addScore(int p) {}
        @Override public int getScore() { return 0; }

        @Override
        public IEventBus getEventBus() {
            return new IEventBus() {
                @Override public <T extends BaseEvent> void subscribe(Class<T> t, Consumer<T> h) {}
                @Override public void publish(BaseEvent e) {}
            };
        }
    }
}
