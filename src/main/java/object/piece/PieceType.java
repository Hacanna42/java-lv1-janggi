package object.piece;

import java.util.Arrays;

public enum PieceType {
    CHARIOT("차"),
    ELEPHANT("상"),
    HORSE("마"),
    GUARD("사"),
    GENERAL("궁"),
    CANNON("포"),
    SOLIDER("졸"),
    ;

    private final String name;

    PieceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static PieceType from(String text) {
        return Arrays.stream(PieceType.values())
                .filter(pieceType -> text.equals(pieceType.name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기물 타입입니다."));
    }
}
