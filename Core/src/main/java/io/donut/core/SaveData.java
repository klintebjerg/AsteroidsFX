package io.donut.core;

import java.util.Set;

public class SaveData {
    private World world;
    private Set<String> loadedMods;
    private long saveTimestamp;

    public SaveData() {}
    public SaveData(World world, Set<String> loadedMods) {
        this.world = world;
        this.loadedMods = loadedMods;
        this.saveTimestamp = System.currentTimeMillis();
    }

    public World getWorld() {
        return world;
    }

    public Set<String> getLoadedMods() {
        return loadedMods;
    }

    public long getSaveTimestamp()
    {
        return saveTimestamp;
    }
}