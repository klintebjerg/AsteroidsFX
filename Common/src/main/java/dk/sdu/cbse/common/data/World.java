package dk.sdu.cbse.common.data;

import java.util.ArrayList;
import java.util.List;

public class World {

    private List<Entity> entityList = new ArrayList<>();

    public void addEntity(Entity entity) {
        entityList.add(entity);
    }

    public void removeEntity(Entity entity){
        entityList.remove(entity);
    }

    public List<Entity> getEntities (){
        return entityList;
    }
}

