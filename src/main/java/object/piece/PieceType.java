package object.piece;

import java.util.Arrays;

public enum PieceType {
    CHARIOT("차"),
    ELEPHANT("상"),
    HORSE("마"),
    GUARD("사"),
    GENERAL("궁"),
    CANNON("포"),
    SOLDIER("졸"),
    ;

    private final String name;

    PieceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
