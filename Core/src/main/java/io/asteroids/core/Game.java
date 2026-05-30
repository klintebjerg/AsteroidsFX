package io.asteroids.core;

import io.asteroids.common.*;

import java.util.List;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class Game {

    private enum GameState { MENU, PLAYING, DEAD, WON }

    private final List<ISystemSPI> systemSPIs;
    private final List<IEntitySPI> entitySPIs;

    private IWorld world;
    private GameState state = GameState.MENU;
    private int finalScore = 0;

    public Game(List<ISystemSPI> systemSPIs, List<IEntitySPI> entitySPIs) {
        this.systemSPIs = systemSPIs;
        this.entitySPIs = entitySPIs;
    }

    public void start() {
        InitWindow(800, 600, "Asteroids");

        while (!WindowShouldClose()) {
            BeginDrawing();
            ClearBackground(BLACK);

            switch (state) {
                case MENU    -> updateMenu();
                case PLAYING -> updatePlaying();
                case DEAD    -> updateDead();
                case WON     -> updateWon();
            }

            EndDrawing();
        }

        CloseWindow();
    }

    private void initWorld() {
        world = new World();
        world.setEventBus(new EventBus());
        world.getEventBus().subscribe(ShipDestroyedEvent.class, e -> {
            finalScore = world.getScore();
            state = GameState.DEAD;
        });
        world.getEventBus().subscribe(AllAsteroidsDestroyedEvent.class, e -> {
            finalScore = world.getScore();
            state = GameState.WON;
        });

        for (ISystemSPI spi : systemSPIs) spi.start(world);
        for (IEntitySPI spi : entitySPIs) spi.start(world);
    }

    private void updateMenu() {
        if (IsKeyPressed(KEY_SPACE)) {
            initWorld();
            state = GameState.PLAYING;
        }
        drawCentered("ASTEROIDS", 150, 60, YELLOW);
        drawCentered("Press SPACE to start", 320, 24, WHITE);
    }

    private void updatePlaying() {
        processInput();
        world.update(GetFrameTime());
        DrawText("Asteroids killed: " + world.getScore(), 10, 10, 20, WHITE);
    }

    private void updateDead() {
        if (IsKeyPressed(KEY_SPACE)) {
            initWorld();
            state = GameState.PLAYING;
        }
        drawCentered("ASTEROIDS", 100, 60, YELLOW);
        drawCentered("GAME OVER", 210, 40, RED);
        drawCentered("Asteroids killed: " + finalScore, 280, 24, WHITE);
        drawCentered("Press SPACE to play again", 360, 20, WHITE);
    }

    private void updateWon() {
        if (IsKeyPressed(KEY_SPACE)) {
            initWorld();
            state = GameState.PLAYING;
        }
        drawCentered("ASTEROIDS", 100, 60, YELLOW);
        drawCentered("YOU WIN!", 210, 40, GREEN);
        drawCentered("Asteroids killed: " + finalScore, 280, 24, WHITE);
        drawCentered("Press SPACE to play again", 360, 20, WHITE);
    }

    private void drawCentered(String text, int y, int fontSize, com.raylib.Raylib.Color color) {
        int x = (GetScreenWidth() - MeasureText(text, fontSize)) / 2;
        DrawText(text, x, y, fontSize, color);
    }

    private void processInput() {
        for (int i = 1; i <= 366; i++) {
            if (IsKeyPressed(i))  world.getEventBus().publish(new KeyPressedEvent(i));
            if (IsKeyReleased(i)) world.getEventBus().publish(new KeyReleasedEvent(i));
            if (IsKeyDown(i))     world.getEventBus().publish(new KeyDownEvent(i));
        }
    }
}
