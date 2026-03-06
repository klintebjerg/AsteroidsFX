package io.donut.core;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class Game {
    public void start() {
        InitWindow(800,600, "Window");

        while(!WindowShouldClose()) {
            BeginDrawing();
            ClearBackground(BLACK);
            DrawText("Hello", 400, 400, 12, RED);
            EndDrawing();
        }

        CloseWindow();
    }
}
