import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

// Note - All the Classes, variables and methods are on package level private
class GolfClubSnapshot {
    private Map<String, Integer> scores = new HashMap<>();
    private Map<String, Integer> scoresArchive = new HashMap<>();
    private LocalDateTime createdAt;
    private boolean restoredSnapshot = false;

    GolfClubSnapshot(Map<String, Integer> scores, Map<String, Integer> scoresArchive) {
        this.scores.putAll(scores);
        this.scoresArchive.putAll(scoresArchive);
        this.createdAt = LocalDateTime.now();
    }

    GolfClubSnapshot(Map<String, Integer> scores, Map<String, Integer> scoresArchive, boolean restored) {
        this.scores.putAll(scores);
        this.scoresArchive.putAll(scoresArchive);
        this.restoredSnapshot = restored;
        this.createdAt = LocalDateTime.now();
    }

    boolean isRestoredSnapshot() {
        return restoredSnapshot;
    }

    Map<String, Integer> getScores() {
        return scores;
    }

    Map<String, Integer> getScoresArchive() {
        return scoresArchive;
    }

    LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
