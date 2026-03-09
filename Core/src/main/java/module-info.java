import io.donut.common.IEntitySPI;
import io.donut.common.ISystemSPI;

module Core {
    requires jaylib;
    requires Common;
    requires com.google.gson;

    uses IEntitySPI;
    uses ISystemSPI;
}