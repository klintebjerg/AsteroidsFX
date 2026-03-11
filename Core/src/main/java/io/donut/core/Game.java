package io.donut.core;
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
    private World world;

    public void start() {
        InitWindow(800, 600, "Window");

        if (world == null) {
            world = new World();
            ServiceLoader<IEntitySPI> entitySPIS = ServiceLoader.load(IEntitySPI.class);
            for (IEntitySPI entitySPI : entitySPIS) {
                entitySPI.start(world);
            }
        }

        ServiceLoader<ISystemSPI> systemSPIS = ServiceLoader.load(ISystemSPI.class);
        for (ISystemSPI systemSPI : systemSPIS) {
            systemSPI.start(world);
        }

        while (!WindowShouldClose()) {
            processInput();

            BeginDrawing();
            ClearBackground(BLACK);

            world.update(GetFrameTime());

            EndDrawing();
        }
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
}
