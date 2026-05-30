import io.asteroids.common.ISystemSPI;
import io.asteroids.enemy.EnemyCollisionSystem;
import io.asteroids.enemy.EnemyMovementSystem;
import io.asteroids.enemy.EnemyShootingSystem;
import io.asteroids.enemy.EnemySpawnerSystem;

module Enemy {
    requires Common;
    requires CommonPhysics;
    requires CommonPlayer;
    requires CommonRender;
    requires jaylib;

    exports io.asteroids.enemy;
    provides ISystemSPI with EnemySpawnerSystem, EnemyMovementSystem, EnemyShootingSystem, EnemyCollisionSystem;
}
