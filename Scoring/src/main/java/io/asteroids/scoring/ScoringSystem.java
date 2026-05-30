package io.asteroids.scoring;

import io.asteroids.common.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScoringSystem extends BaseSystem implements ISystemSPI {

    private static final String BASE_URL = "http://localhost:8080";
    private static final RestTemplate REST = new RestTemplate();
    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r, "scoring-http");
        t.setDaemon(true);
        return t;
    });

    public ScoringSystem() {
        setPriority(99);
    }

    @Override
    public void start(IWorld world) {
        world.addSystem(this);
        world.getEventBus().subscribe(ScoreAddedEvent.class, e ->
            EXECUTOR.submit(() -> post("/score/add?points=" + e.points))
        );
        EXECUTOR.submit(() -> post("/score/reset"));
    }

    @Override
    public void update(IWorld world, List<BaseEntity> entities, float deltaTime) {}

    @Override
    public void stop(IWorld world) {}

    @Override
    public List<Class<? extends BaseComponent>> getSignature() {
        return List.of();
    }

    private static void post(String path) {
        try {
            REST.postForObject(BASE_URL + path, null, String.class);
        } catch (Exception ignored) {
            // Scoring service not running — fail silently
        }
    }
}
