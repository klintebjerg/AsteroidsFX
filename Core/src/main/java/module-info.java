import io.donut.common.IEntitySPI;
import io.donut.common.ISystemSPI;

module Core {
    requires jaylib;
    requires Common;

    uses IEntitySPI;
    uses ISystemSPI;
}