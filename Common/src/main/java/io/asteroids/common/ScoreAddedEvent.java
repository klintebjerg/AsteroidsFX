package io.asteroids.common;

public class ScoreAddedEvent extends BaseEvent {
    public final int points;

    public ScoreAddedEvent(int points) {
        this.points = points;
    }
}
