package io.asteroids.common;

import java.util.List;

public abstract class IteratingSystem extends BaseSystem {

    /// The update method for the iterating system is already implemented
    /// to process each entity by iterating over them.
    /// @param world the world implementation passed to the system.
    /// @param entities the entities to process.
    /// @param deltaTime the frame-time.
    @Override
    public void update(IWorld world, List<BaseEntity> entities, float deltaTime){
        for(BaseEntity entity : entities){
            processEntity(world, entity, deltaTime);
        }
    }

    /// Is called per entity with the given signature.
    /// @param world the world everything lives in.
    /// @param entity an entity that matches the signature.
    /// @param deltaTime the time since last frame.
    public abstract void processEntity(IWorld world, BaseEntity entity, float deltaTime);
}
