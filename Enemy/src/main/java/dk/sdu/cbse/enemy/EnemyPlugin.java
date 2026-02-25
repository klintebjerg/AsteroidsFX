package dk.sdu.cbse.enemy;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {
    private Enemy enemy;

    @Override
    public void start(GameData gameData, World world) {
        enemy = new Enemy();
        world.addEntity(enemy);

        enemy.setX(gameData.getDisplayWidth() / 2);
        enemy.setY(gameData.getDisplayHeight() / 2);
        enemy.setPolygonCoordinates(-5,-5,10,0,-5,5);
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
    }
}
