package io.asteroids.core;

import io.asteroids.common.*;

import java.util.ServiceLoader;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class Game {
    private IWorld world;

    public void start() {
        InitWindow(800, 600, "Window");

        world = new World();
        world.setEventBus(new EventBus());

        ServiceLoader<ISystemSPI> systemSPIS = ServiceLoader.load(ISystemSPI.class);
        for (ISystemSPI systemSPI : systemSPIS) {
            systemSPI.start(world);
        }

        ServiceLoader<IEntitySPI> entitySPIS = ServiceLoader.load(IEntitySPI.class);
        for (IEntitySPI entitySPI : entitySPIS) {
            entitySPI.start(world);
        }

        while (!WindowShouldClose()) {
            processInput();

            BeginDrawing();
            ClearBackground(WHITE);
            world.update(GetFrameTime());
            EndDrawing();
        }

        CloseWindow();
    }

    private void processInput() {
        for (int i = 1; i <= 366; i++) {
            if (IsKeyPressed(i)) {
                world.getEventBus().publish(new KeyPressedEvent(i));
            }
            if (IsKeyReleased(i)) {
                world.getEventBus().publish(new KeyReleasedEvent(i));
            }
            if (IsKeyDown(i)) {
                world.getEventBus().publish(new KeyDownEvent(i));
            }
        }
    }
}
