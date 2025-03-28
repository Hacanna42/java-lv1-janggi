package object.moverule;

import java.util.List;
import object.coordinate.Position;
import object.coordinate.Path;
import object.piece.Piece;
import object.piece.PieceType;
import object.piece.Team;

public class GeneralRule extends MoveRule {

    // step2에서 구현 예정: 장기판의 왕(將, 帥)과 사(士, 仕)의 이동과 관련된 규칙은 고려하지 않는다.
    private static final String NOT_IMPLEMENTED_FEATURE = "아직 구현되지 않은 기능입니다.";

    @Override
    public Path getLegalRoute(Position startPosition, Position endPosition, Team team) {
        throw new IllegalArgumentException(NOT_IMPLEMENTED_FEATURE);
    }

    @Override
    public boolean isAbleToThrough(Path legalPath, List<Piece> piecesOnBoard, Team team) {
        return false;
    }


    public PieceType getPieceType() {
        return PieceType.GENERAL;
    }
}
