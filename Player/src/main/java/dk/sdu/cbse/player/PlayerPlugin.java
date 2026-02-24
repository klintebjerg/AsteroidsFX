package dk.sdu.cbse.player;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IGamePluginService;

public class PlayerPlugin implements IGamePluginService {
    private Player player;

    @Override
    public void start(GameData gameData, World world) {
        player = new Player();
        world.addEntity(player);

        player.setX(gameData.getDisplayWidth() / 2);
        player.setY(gameData.getDisplayHeight() / 2);
        player.setPolygonCoordinates(-5,-5,10,0,-5,5);
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player);
    }
}
