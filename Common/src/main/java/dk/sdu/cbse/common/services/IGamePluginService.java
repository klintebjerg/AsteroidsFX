package dk.sdu.cbse.common.services;


import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;

public interface IGamePluginService {
    public void start(GameData gameData, World world);
    public void stop(GameData gameData, World world);
}
