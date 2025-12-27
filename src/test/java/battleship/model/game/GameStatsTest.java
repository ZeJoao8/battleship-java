package battleship.model.game;

import battleship.model.game.GameStats;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStatsTest {

    @Test
    void startAndFinishWithTimer() {
        GameStats stats = new GameStats();
        stats.start();
        stats.finish();
        assertTrue(stats.getDurationSeconds() >= 0);
    }

    @Test
    void startAndFinishWithoutTimer() {
        GameStats stats = new GameStats(null);
        stats.start();
        stats.finish();
        assertEquals(0, stats.getDurationSeconds());
    }

    @Test
    void durationUsesFinalSecondsWhenFinished() {
        GameStats stats = new GameStats();
        stats.start();
        stats.finish();
        int duration = stats.getDurationSeconds();
        assertTrue(duration >= 0);
    }

    @Test
    void durationUsesTimerWhenNotFinished() {
        GameStats stats = new GameStats();
        stats.start();
        int duration = stats.getDurationSeconds();
        assertTrue(duration >= 0);
    }

    @Test
    void durationZeroWhenNoTimerAndNotFinished() {
        GameStats stats = new GameStats(null);
        assertEquals(0, stats.getDurationSeconds());
    }

    @Test
    void registerPlayerShotHitAndMiss() {
        GameStats stats = new GameStats();
        stats.registerPlayerShot(true);
        stats.registerPlayerShot(false);
        assertEquals(2, stats.getPlayerShots());
        assertEquals(1, stats.getPlayerHits());
    }

    @Test
    void registerBotShotHitAndMiss() {
        GameStats stats = new GameStats();
        stats.registerBotShot(true);
        stats.registerBotShot(false);
        assertEquals(2, stats.getBotShots());
        assertEquals(1, stats.getBotHits());
    }

    @Test
    void playerAccuracyZeroWhenNoShots() {
        GameStats stats = new GameStats();
        assertEquals(0, stats.getPlayerAccuracy());
    }

    @Test
    void botAccuracyZeroWhenNoShots() {
        GameStats stats = new GameStats();
        assertEquals(0, stats.getBotAccuracy());
    }

    @Test
    void playerAccuracyComputedCorrectly() {
        GameStats stats = new GameStats();
        stats.registerPlayerShot(true);
        stats.registerPlayerShot(false);
        assertEquals(50.0, stats.getPlayerAccuracy());
    }

    @Test
    void botAccuracyComputedCorrectly() {
        GameStats stats = new GameStats();
        stats.registerBotShot(true);
        stats.registerBotShot(true);
        stats.registerBotShot(false);
        assertEquals(66.66666666666667, stats.getBotAccuracy());
    }

    @Test
    void finalModelGetterAndSetter() {
        GameStats stats = new GameStats();
        BattleModel model = null;
        stats.setFinalModel(model);
        assertNull(stats.getFinalModel());
    }
}
