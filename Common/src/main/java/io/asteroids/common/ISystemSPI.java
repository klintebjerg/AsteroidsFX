package io.asteroids.common;

public interface ISystemSPI {
    /**
     * Called when the world is first initialized. Used to add systems to the world.
     * @param world the world to add the system in.
     */
    void start(IWorld world);
    /**
     * Called when the world is ending. Used to clean up resources.
     * @param world the world the system was in.
     */
    void stop(IWorld world);
}
