package object.piece;

import java.util.Arrays;
import java.util.function.Supplier;
import object.moverule.CannonRule;
import object.moverule.ChariotRule;
import object.moverule.ElephantRule;
import object.moverule.GeneralRule;
import object.moverule.GuardRule;
import object.moverule.HorseRule;
import object.moverule.MoveRule;
import object.moverule.SoldierRule;

public enum PieceType {
    CHARIOT("차", ChariotRule::new),
    ELEPHANT("상", ElephantRule::new),
    HORSE("마", HorseRule::new),
    GUARD("사", GuardRule::new),
    GENERAL("궁", GeneralRule::new),
    CANNON("포", CannonRule::new),
    SOLDIER("졸", SoldierRule::new),
    ;

    private final String name;
    private final Supplier<MoveRule> ruleSupplier;

    PieceType(String name, Supplier<MoveRule> ruleSupplier) {
        this.name = name;
        this.ruleSupplier = ruleSupplier;
    }

    public static PieceType from(String value) {
        for (PieceType pieceType : PieceType.values()) {
            if (pieceType.name.equals(value)) {
                return pieceType;
            }
        }
        throw new IllegalArgumentException("올바른 기물 타입을 매핑하는데에 실패했습니다.");
    }

    public String getName() {
        return name;
    }

    public MoveRule createMoveRule() {
        return ruleSupplier.get();
    }
}
