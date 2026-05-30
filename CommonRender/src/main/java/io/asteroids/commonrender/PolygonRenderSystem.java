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
        PolygonComponent poly   = entity.getComponent(PolygonComponent.class);
        PositionComponent pos   = entity.getComponent(PositionComponent.class);
        AngleComponent    angle = entity.getComponent(AngleComponent.class);

        float[] coords = poly.coordinates;
        int count = coords.length / 2;
        AsteroidsVector[] points = new AsteroidsVector[count];

        for (int i = 0; i < count; i++) {
            points[i] = rotatePoint(coords[i * 2], coords[i * 2 + 1], angle.ang,
                    pos.pos.x(), pos.pos.y());
        }

        if (poly.fillColor != null) {
            for (int i = 1; i < count - 1; i++) {
                DrawTriangle(points[0], points[i], points[i + 1], poly.fillColor);
            }
        }

        Color outline = poly.fillColor != null ? poly.fillColor : BLUE;
        for (int i = 0; i < count; i++) {
            DrawLineV(points[i], points[(i + 1) % count], outline);
        }
    }

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of(PolygonComponent.class, PositionComponent.class, AngleComponent.class);
    }

    private AsteroidsVector rotatePoint(float px, float py, float angleDeg, float tx, float ty) {
        float rad = (float) Math.toRadians(angleDeg);
        float cos = (float) Math.cos(rad);
        float sin = (float) Math.sin(rad);
        return new AsteroidsVector(
                px * cos - py * sin + tx,
                px * sin + py * cos + ty);
    }
}
