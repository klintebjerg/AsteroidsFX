package io.asteroids.player;

import io.asteroids.common.AsteroidsVector;
import io.asteroids.common.BaseEntity;
import io.asteroids.common.IEntitySPI;
import io.asteroids.common.IWorld;
import io.asteroids.commonphysics.AngleComponent;
import io.asteroids.commonphysics.PositionComponent;
import io.asteroids.commonphysics.VelocityComponent;
import io.asteroids.commonrender.PolygonComponent;

public class PlayerEntity extends BaseEntity implements IEntitySPI {

    @Override
    public void start(IWorld world) {
        world.addEntity(new PlayerEntity());
    }

    public PlayerEntity() {
        PositionComponent positionComponent = new PositionComponent();
        positionComponent.pos = new AsteroidsVector(0,0);
        this.addComponent(positionComponent);

        VelocityComponent velocityComponent = new VelocityComponent();
        velocityComponent.vel = new AsteroidsVector(0,0);
        this.addComponent(velocityComponent);

        AngleComponent angleComponent = new AngleComponent();
        angleComponent.ang = 90;
        this.addComponent(angleComponent);

        PolygonComponent polygonComponent = new PolygonComponent();
        polygonComponent.coordinates = new float[]{ 20,0, -10,-12, -10,12 };
        this.addComponent(polygonComponent);
    }

    @Override
    public void stop(IWorld world) {

    }
}
