package io.asteroids.common;

public class KeyPressedEvent extends BaseEvent{
    public int key;
    public KeyPressedEvent(int key){
        this.key = key;
    }
}
