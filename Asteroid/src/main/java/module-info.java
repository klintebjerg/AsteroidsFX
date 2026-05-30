import io.asteroids.common.ISystemSPI;
import io.asteroids.asteroid.AsteroidSpawnerSystem;
import io.asteroids.asteroid.AsteroidCollisionSystem;

module Asteroid {
    requires Common;
    requires CommonPhysics;
    requires CommonPlayer;
    requires CommonRender;
    requires jaylib;

    exports io.asteroids.asteroid;
    provides ISystemSPI with AsteroidSpawnerSystem, AsteroidCollisionSystem;
}
