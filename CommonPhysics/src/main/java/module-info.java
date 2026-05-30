import io.asteroids.common.ISystemSPI;
import io.asteroids.commonphysics.LifetimeSystem;
import io.asteroids.commonphysics.MovementSystem;
import io.asteroids.commonphysics.ScreenWrapSystem;

module CommonPhysics {
    exports io.asteroids.commonphysics;
    requires Common;
    requires jaylib;

    provides ISystemSPI with MovementSystem, LifetimeSystem, ScreenWrapSystem;
}