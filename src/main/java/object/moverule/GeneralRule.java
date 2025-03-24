package object.moverule;

import java.util.List;
import object.Coordinate;
import object.Route;
import object.piece.Piece;
import object.piece.PieceType;
import object.piece.GameBoard;
import object.piece.Team;

public class GeneralRule implements MoveRule {

    private static final String NOT_IMPLEMENTED_FEATURE = "아직 구현되지 않은 기능입니다.";

    @Override
    public Route getLegalRoute(Coordinate startCoordinate, Coordinate endCoordinate, Team team) {
        throw new IllegalStateException(NOT_IMPLEMENTED_FEATURE);
    }

    @Override
    public boolean isAbleToThrough(Route legalRoute, List<Piece> piecesOnBoard, Team team) {
        return false;
    }


    public PieceType getPieceType() {
        return PieceType.GENERAL;
    }
}
