package dk.sdu.cbse.common.data;

import java.io.Serializable;
import java.util.UUID;

public class Entity implements Serializable {

    private final UUID ID = UUID.randomUUID();
    private float[] polygonCoordinates;
    private float x;
    private float y;
    private float rotation;
    private float radius;

    public String getID() {
        return ID.toString();
    }

    public void setPolygonCoordinates(float... coordinates){
        this.polygonCoordinates = coordinates;
    }

    public float[] getPolygonCoordinates() {
        return polygonCoordinates;
    }

    public void setX(float x){
        this.x = x;
    }

    public float getX() {
        return x;
    }

    public void setY(float y){
        this.y = y;
    }

    public float getY() {
        return y;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRadius(float r) {
        this.radius = r;
    }

    public float getRadius () {
        return radius;
    }
}
