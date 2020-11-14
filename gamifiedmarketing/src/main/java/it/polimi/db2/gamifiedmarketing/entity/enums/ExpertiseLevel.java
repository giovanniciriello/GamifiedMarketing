package it.polimi.db2.gamifiedmarketing.entity.enums;

public enum ExpertiseLevel {
    LOW(0), MEDIUM(1), HIGH(2);

    private final int value;

    ExpertiseLevel(int value) {
        this.value = value;
    }

    public static ExpertiseLevel getExpertiseLevelFromInt(int value) {
        switch (value) {
            case 0:
                return ExpertiseLevel.LOW;
            case 1:
                return ExpertiseLevel.MEDIUM;
            case 2:
                return ExpertiseLevel.HIGH;
        }
        return null;
    }

    public int getValue() {
        return value;
    }
}
