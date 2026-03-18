import io.asteroids.common.IEntitySPI;
import io.asteroids.common.ISystemSPI;

module Core {
    requires jaylib;
    requires Common;
    requires CommonRender;

    uses IEntitySPI;
    uses ISystemSPI;
}