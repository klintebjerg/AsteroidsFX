import io.asteroids.common.ISystemSPI;
import io.asteroids.weapon.WeaponSystem;

module Weapon {
    requires Common;
    requires CommonPhysics;
    requires CommonPlayer;
    requires CommonRender;
    requires jaylib;

    provides ISystemSPI with WeaponSystem;
}
