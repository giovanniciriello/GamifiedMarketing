package it.polimi.db2.gamifiedmarketing.entity;

public enum SubStatus {
    CREATED(0), CANCELED(1), CONFIRMED(2);

    private final int value;

    SubStatus(int value) {
        this.value = value;
    }

    public static SubStatus getSubStatusFromInt(int value) {
        switch (value) {
            case 0:
                return SubStatus.CREATED;
            case 1:
                return SubStatus.CANCELED;
            case 2:
                return SubStatus.CONFIRMED;
        }
        return null;
    }

    public int getValue() {
        return value;
    }
}
