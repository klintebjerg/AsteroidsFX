package io.asteroids.commonphysics;

import io.asteroids.common.BaseComponent;

public class LifetimeComponent extends BaseComponent {
    public float remainingLife;

    public LifetimeComponent(float remainingLife) {
        this.remainingLife = remainingLife;
    }
}
