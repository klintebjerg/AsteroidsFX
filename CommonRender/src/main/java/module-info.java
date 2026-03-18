import io.asteroids.common.ISystemSPI;
import io.asteroids.commonrender.PolygonRenderSystem;

module CommonRender {
    requires Common;
    requires CommonPhysics;
    requires jaylib;

    exports io.asteroids.commonrender;
    provides ISystemSPI with PolygonRenderSystem;
}