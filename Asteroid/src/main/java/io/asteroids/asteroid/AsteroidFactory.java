package io.asteroids.asteroid;

import io.asteroids.common.AsteroidsVector;
import io.asteroids.common.BaseEntity;
import io.asteroids.common.IWorld;
import io.asteroids.commonphysics.AngleComponent;
import io.asteroids.commonphysics.PositionComponent;
import io.asteroids.commonphysics.VelocityComponent;
import io.asteroids.commonrender.PolygonComponent;
import static com.raylib.Colors.DARKGRAY;

public class AsteroidFactory {

    public static void spawnAsteroid(IWorld world, AsteroidsVector pos,
                                     AsteroidComponent.Size size,
                                     float velAngleDeg, float speed) {
        BaseEntity asteroid = new BaseEntity() {};

        asteroid.addComponent(new PositionComponent(new AsteroidsVector(pos.x(), pos.y())));

        VelocityComponent vel = new VelocityComponent();
        vel.vel = AsteroidsVector.fromAngleWithMag(velAngleDeg, speed);
        asteroid.addComponent(vel);

        AngleComponent ang = new AngleComponent();
        ang.ang = (float) (Math.random() * 360);
        asteroid.addComponent(ang);

        PolygonComponent poly = new PolygonComponent();
        poly.coordinates = coordsFor(size);
        poly.fillColor = DARKGRAY;
        asteroid.addComponent(poly);

        asteroid.addComponent(new AsteroidComponent(size));

        world.addEntity(asteroid);
    }

    public static float radiusFor(AsteroidComponent.Size size) {
        return switch (size) {
            case LARGE  -> 40f;
            case MEDIUM -> 20f;
            case SMALL  -> 10f;
        };
    }

    private static float[] coordsFor(AsteroidComponent.Size size) {
        return switch (size) {
            case LARGE  -> new float[]{ 0,-42,  20,-32,  42,-12,  38,14,  22,42,  -14,38,  -42,12,  -30,-20 };
            case MEDIUM -> new float[]{ 0,-21,  10,-16,  21,-6,   19,7,   11,21,  -7,19,   -21,6,   -15,-10 };
            case SMALL  -> new float[]{ 0,-10,  5,-8,    10,-3,   9,3,    5,10,   -3,9,    -10,3,   -7,-5   };
        };
    }
}
