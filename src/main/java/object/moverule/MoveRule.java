package object.moverule;

import java.util.List;
import java.util.Optional;
import object.coordinate.Position;
import object.coordinate.Path;
import object.piece.Piece;
import object.piece.PieceType;
import object.piece.Team;

public abstract class MoveRule {

    protected static final String INVALID_POSITION = "도달할 수 없는 위치입니다.";

    public abstract PieceType getPieceType();

    public final void checkAbleToMove(Position from, Position to, List<Piece> piecesOnBoard, Team team) {
        Path legalPath = getLegalRoute(from, to, team);
        if (legalPath.getSize() == 0) {
            throw new IllegalArgumentException("제자리로 이동할 수 없습니다.");
        }
        if (!isAbleToThrough(legalPath, piecesOnBoard, team)) {
            throw new IllegalArgumentException(INVALID_POSITION);
        }
    }

    protected final Optional<Piece> findFirstPieceOnRoute(Path path, List<Piece> piecesOnBoard) {
        for (Position position : path.getPositions()) {
            Optional<Piece> foundPiece = piecesOnBoard.stream()
                    .filter(piece -> piece.isSamePosition(position))
                    .findFirst();

            if (foundPiece.isPresent()) {
                return foundPiece;
            }
        }

        return Optional.empty();
    }

    protected abstract Path getLegalRoute(Position startPosition, Position endPosition, Team team);
    protected abstract boolean isAbleToThrough(Path legalPath, List<Piece> piecesOnBoard, Team team);

    @Override
    public boolean equals(Object obj) {
        return obj != null && this.getClass() == obj.getClass();
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
