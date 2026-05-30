package io.asteroids.asteroid;

import io.asteroids.common.BaseComponent;

public class AsteroidComponent extends BaseComponent {

    public enum Size { LARGE, MEDIUM, SMALL }

    public Size size;

    public AsteroidComponent(Size size) {
        this.size = size;
    }
}
