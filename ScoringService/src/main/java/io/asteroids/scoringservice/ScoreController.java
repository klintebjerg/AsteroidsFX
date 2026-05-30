package io.asteroids.scoringservice;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/score")
public class ScoreController {

    private final AtomicInteger score = new AtomicInteger(0);

    @GetMapping
    public Map<String, Integer> getScore() {
        return Map.of("score", score.get());
    }

    @PostMapping("/add")
    public Map<String, Integer> addScore(@RequestParam int points) {
        return Map.of("score", score.addAndGet(points));
    }

    @PostMapping("/reset")
    public Map<String, Integer> resetScore() {
        score.set(0);
        return Map.of("score", 0);
    }
}
