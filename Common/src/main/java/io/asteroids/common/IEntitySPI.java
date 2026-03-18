package io.asteroids.common;

public interface IEntitySPI {
    /**
     * Called when the world is first initialized. Used to add entities to the world.
     * @param world the world to add the entity in.
     */
    void start(IWorld world);

    /**
     * Called when the world is ending. Used to clean up resources.
     * @param world the world the entity was in.
     */
    void stop(IWorld world);
}
