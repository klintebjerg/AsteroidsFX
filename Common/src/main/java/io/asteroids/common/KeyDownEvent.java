package io.asteroids.common;

public class KeyDownEvent extends BaseEvent {
    public int key;
    public KeyDownEvent(int key) {
        this.key = key;
    }
}
