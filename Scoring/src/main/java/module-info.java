import io.asteroids.common.ISystemSPI;
import io.asteroids.scoring.ScoringSystem;

module Scoring {
    requires Common;
    requires spring.web;

    provides ISystemSPI with ScoringSystem;
}
