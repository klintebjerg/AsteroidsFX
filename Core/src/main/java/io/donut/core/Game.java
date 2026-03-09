package io.donut.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.donut.common.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class Game {
    private final Gson gson = new GsonBuilder().setPrettyPrinting()
            .registerTypeHierarchyAdapter(BaseEntity.class, new PolymorphicAdapter<>())
            .registerTypeHierarchyAdapter(BaseComponent.class, new PolymorphicAdapter<>())
            .create();

    private static final String SAVE_FILE = "savegame.json";

    private World world;

    public void start() {
        InitWindow(800, 600, "Window");

        world = loadGame();
        if (world == null) {
            world = new World();
            ServiceLoader<IEntitySPI> entitySPIS = ServiceLoader.load(IEntitySPI.class);
            for (IEntitySPI entitySPI : entitySPIS) {
                entitySPI.start(world);
            }
        }

        // LAD VÆR AT RYK DEN HER TAK
        ServiceLoader<ISystemSPI> systemSPIS = ServiceLoader.load(ISystemSPI.class);
        for (ISystemSPI systemSPI : systemSPIS) {
            systemSPI.start(world);
        }

        while (!WindowShouldClose()) {
            processInput();

            BeginDrawing();
            ClearBackground(BLACK);

            world.update(GetFrameTime()); // Definerer en flyde punkt, bro hvad fuck er det her, det er flyde punkt bro. Hvad fuck er det. Flyde.

            EndDrawing();
        }

        // TODO: Should we save while running as well?
        saveGame(world);
        CloseWindow();
    }

    private void processInput(){
        int key;
        while((key = GetKeyPressed()) != 0){
            world.getEventBus().publish(new KeyPressedEvent(key));
        }

        for(int i = 1; i <= 366; i++){ // Should be all keys, hopefully
            if(IsKeyReleased(i)){
                world.getEventBus().publish(new KeyReleasedEvent(i));
            }
        }
    }

    private Set<String> getLoadedModIdentifiers() {
        Set<String> mods = new HashSet<>();
        ServiceLoader.load(IEntitySPI.class)
                .forEach(spi -> mods.add(spi.getClass().getName()));
        ServiceLoader.load(ISystemSPI.class)
                .forEach(spi -> mods.add(spi.getClass().getName()));

        return mods;
    }

    public void saveGame(World world) {
        Path path = Path.of(SAVE_FILE);

        try {
            SaveData saveData = new SaveData(world, getLoadedModIdentifiers());
            String json = gson.toJson(saveData);
            Files.writeString(path, json);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to save game: " + e.getMessage());
            // TODO: Open dialog/game popup and notify the user
        }
    }

    public World loadGame() {
        Path path = Path.of(SAVE_FILE);
        if (!Files.exists(path)) {
            return null; // No save file found, start a new game
        }

        try {
            String json = Files.readString(path);
            SaveData saveData = gson.fromJson(json, SaveData.class);

            // TODO: We need to wait for this operation and or the user to confirm before we load the game.
            // Otherwise we might run into issues if the user has removed mods that are required for the save file.
            checkModificationChanges(saveData);

            World world = saveData.getWorld();
            if (world != null) {
                world.removeNullEntities();
            }

            return world;
        } catch (IOException e) {
            System.err.println("Failed to load game: " + e.getMessage());
            // TODO: Open dialog/game popup and notify the user
        }
        return null;
    }

    private void checkModificationChanges(SaveData saveData) {
        try {
            Set<String> currentMods = getLoadedModIdentifiers();
            Set<String> savedMods = saveData.getLoadedMods();

            Set<String> removedMods = new HashSet<>(savedMods);
            removedMods.removeAll(currentMods);

            Set<String> addedMods = new HashSet<String>(currentMods);
            addedMods.removeAll(savedMods);

            if(!removedMods.isEmpty() || !addedMods.isEmpty()) {
                System.out.println("The following mods were removed / added since the last save:");
                removedMods.forEach(mod -> System.out.println("- REMOVED: " + mod));
                addedMods.forEach(mod -> System.out.println("- ADDED: " + mod));
                // TODO: Show dialog to user. Figure out what we do here.
            }
        }
        catch (Exception e) {
            System.err.println("Failed to check mod changes: " + e.getMessage());
        }

    }
}
