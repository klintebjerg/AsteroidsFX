package io.asteroids.common;

public class KeyReleasedEvent extends BaseEvent{
    public int key;
    public KeyReleasedEvent(int key){
        this.key = key;
    }
}
