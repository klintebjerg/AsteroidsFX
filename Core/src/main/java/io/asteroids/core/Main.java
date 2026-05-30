package io.asteroids.core;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(GameConfig.class);
        var game = ctx.getBean(Game.class);
        game.start();
    }
}
