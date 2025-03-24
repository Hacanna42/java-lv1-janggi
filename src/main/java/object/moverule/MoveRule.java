package object.moverule;

import java.util.List;
import java.util.Optional;
import object.Coordinate;
import object.Path;
import object.piece.Piece;
import object.piece.PieceType;
import object.piece.Team;

public interface MoveRule {

    String INVALID_POSITION = "도달할 수 없는 위치입니다.";

    default void checkAbleToMove(Coordinate from, Coordinate to, List<Piece> piecesOnBoard, Team team) {
        Path legalPath = getLegalRoute(from, to, team);
        if (legalPath.getSize() == 0) {
            throw new IllegalArgumentException("제자리로 이동할 수 없습니다.");
        }
        if (!isAbleToThrough(legalPath, piecesOnBoard, team)) {
            throw new IllegalArgumentException(INVALID_POSITION);
        }
    }

    Path getLegalRoute(Coordinate startCoordinate, Coordinate endCoordinate, Team team);
    boolean isAbleToThrough(Path legalPath, List<Piece> piecesOnBoard, Team team);
    PieceType getPieceType();

    default Optional<Piece> findFirstPieceOnRoute(Path path, List<Piece> piecesOnBoard) {
        for (Coordinate coordinate : path.getCoordinate()) {
            Optional<Piece> foundPiece = piecesOnBoard.stream()
                    .filter(piece -> piece.isSameCoordinate(coordinate))
                    .findFirst();

            if (foundPiece.isPresent()) {
                return foundPiece;
            }
        }

        return Optional.empty();
    }
}
