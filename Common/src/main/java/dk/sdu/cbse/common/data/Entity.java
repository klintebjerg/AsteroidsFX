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

    public void setPolygonCoordinates(double)
}
