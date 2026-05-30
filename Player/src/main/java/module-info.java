import io.asteroids.common.IEntitySPI;
import io.asteroids.common.ISystemSPI;
import io.asteroids.player.PlayerEntity;
import io.asteroids.player.PlayerMovementSystem;

module Player {
    requires Common;
    requires CommonPhysics;
    requires CommonPlayer;
    requires jaylib;
    requires CommonRender;

    exports io.asteroids.plugin;

    provides IEntitySPI with PlayerEntity;
    provides ISystemSPI with PlayerMovementSystem;
}
