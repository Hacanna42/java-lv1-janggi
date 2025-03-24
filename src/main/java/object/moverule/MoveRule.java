package object.moverule;

import java.util.List;
import java.util.Optional;
import object.Coordinate;
import object.Route;
import object.piece.Piece;
import object.piece.PieceType;
import object.piece.Team;

public interface MoveRule {

    String INVALID_POSITION = "도달할 수 없는 위치입니다.";

    default void checkAbleToMove(Coordinate fromCoordinate, Coordinate toCoordinate, List<Piece> piecesOnBoard, Team team) {
        Route legalRoute = getLegalRoute(fromCoordinate, toCoordinate, team);
        if (!isAbleToThrough(legalRoute, piecesOnBoard, team)) {
            throw new IllegalArgumentException(INVALID_POSITION);
        }
    }

    default Optional<Piece> findFirstPieceOnRoute(Route route, List<Piece> piecesOnBoard) {
        for (Coordinate coordinate : route.getCoordinate()) {
            Optional<Piece> foundPiece = piecesOnBoard.stream()
                    .filter(piece -> piece.isSameCoordinate(coordinate))
                    .findFirst();

            if (foundPiece.isPresent()) {
                return foundPiece;
            }
        }

        return Optional.empty();
    }

    Route getLegalRoute(Coordinate startCoordinate, Coordinate endCoordinate, Team team);
    boolean isAbleToThrough(Route legalRoute, List<Piece> piecesOnBoard, Team team);
    PieceType getPieceType();
}
