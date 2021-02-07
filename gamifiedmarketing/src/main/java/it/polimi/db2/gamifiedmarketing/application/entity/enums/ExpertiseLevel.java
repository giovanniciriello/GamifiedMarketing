package it.polimi.db2.gamifiedmarketing.application.entity.enums;

public enum ExpertiseLevel {
    LOW(0), MEDIUM(1), HIGH(2), NONE(3);

    private final int value;

    ExpertiseLevel(int value) {
        this.value = value;
    }

    public static ExpertiseLevel getExpertiseLevelFromInt(int value) {
        return switch (value) {
            case 0 -> ExpertiseLevel.LOW;
            case 1 -> ExpertiseLevel.MEDIUM;
            case 2 -> ExpertiseLevel.HIGH;
            default -> null;
        };

    }

    public ExpertiseLevel getExpertiseLevel() {
        return getExpertiseLevelFromInt(value);
    }

    public Integer getValue() {
        return value;
    }
}
