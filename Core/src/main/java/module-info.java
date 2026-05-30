import io.asteroids.common.IEntitySPI;
import io.asteroids.common.ISystemSPI;

module Core {
    requires jaylib;
    requires Common;
    requires CommonRender;
    requires spring.context;
    requires spring.beans;
    requires spring.core;

    uses IEntitySPI;
    uses ISystemSPI;

    opens io.asteroids.core to spring.core, spring.beans, spring.context;
}
