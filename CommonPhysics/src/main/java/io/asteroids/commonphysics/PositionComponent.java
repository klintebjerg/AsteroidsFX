package io.asteroids.commonphysics;

import io.asteroids.common.AsteroidsVector;
import io.asteroids.common.BaseComponent;

public class PositionComponent extends BaseComponent {
    public AsteroidsVector pos;

    public PositionComponent(AsteroidsVector pos) {
        this.pos = pos;
    }

    public PositionComponent() {}
}
