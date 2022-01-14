package learn.sfg.sfgrecipe.domain;

public enum Difficulty {
    EASY("Easy"),
    MODERATE("Moderate"),
    KIND_OF_HARD("Kind of hard"),
    HARD("Hard");

    private final String displayName;

    Difficulty(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Difficulty from(String value) {
        for (Difficulty difficulty : Difficulty.values()) {
            if (difficulty.displayName.equals(value)) {
                return difficulty;
            }
        }
        throw new IllegalArgumentException("Cannot convert value " + value);
    }
}
