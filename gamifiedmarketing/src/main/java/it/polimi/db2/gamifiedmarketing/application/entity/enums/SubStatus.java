package it.polimi.db2.gamifiedmarketing.application.entity.enums;

public enum SubStatus {
    CANCELED(0), CONFIRMED(1);

    private final int value;

    SubStatus(int value) {
        this.value = value;
    }

    public static SubStatus getSubStatusFromInt(int value) {
        return switch (value) {
            case 0 -> SubStatus.CANCELED;
            case 1 -> SubStatus.CONFIRMED;
            default -> null;
        };
    }

    public int getValue() {
        return value;
    }
}
