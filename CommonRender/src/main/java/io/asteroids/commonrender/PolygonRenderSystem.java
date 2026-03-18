package io.asteroids.commonrender;

import io.asteroids.common.*;
import io.asteroids.commonphysics.*;

import java.util.List;
import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class PolygonRenderSystem extends IteratingSystem implements ISystemSPI {

    @Override
    public void start(IWorld world) {
        world.addSystem(this);
    }

    @Override
    public void stop(IWorld world) {}

    @Override
    public void processEntity(IWorld world, BaseEntity entity, float deltaTime) {
        PolygonComponent polygonComponent = entity.getComponent(PolygonComponent.class);
        PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
        AngleComponent angleComponent = entity.getComponent(AngleComponent.class);

        float[] coords = polygonComponent.coordinates;
        int count = coords.length / 2;
        AsteroidsVector[] points = new AsteroidsVector[count];

        for (int i = 0; i < count; i++) {
            points[i] = rotatePoint(coords[i*2], coords[i*2+1], angleComponent.ang, positionComponent.pos.x(), positionComponent.pos.y());
        }

        for (int i = 0; i < count; i++) {
            DrawLineV(points[i], points[(i+1) % count], BLACK);
        }
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PolygonComponent.class, PositionComponent.class, VelocityComponent.class);
    }

    private AsteroidsVector rotatePoint(float px, float py, float angleDeg, float tx,
                                        float ty) {
        float rad = (float) Math.toRadians(angleDeg);
        float cos = (float) Math.cos(rad);
        float sin = (float) Math.sin(rad);
        return new AsteroidsVector(
                px * cos - py * sin + tx,
                px * sin + py * cos + ty
        );
    }
}
