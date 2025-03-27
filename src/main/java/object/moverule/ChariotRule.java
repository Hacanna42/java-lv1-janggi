package object.moverule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import object.coordinate.Position;
import object.coordinate.Path;
import object.coordinate.RelativePosition;
import object.piece.Piece;
import object.piece.PieceType;
import object.piece.Team;

public class ChariotRule extends MoveRule {

    @Override
    public Path getLegalRoute(Position startPosition, Position endPosition, Team team) {
        Position maxPosition = Position.getAbsoluteBigPositionBetween(startPosition, endPosition);

        if (startPosition.isSameRow(endPosition)) {
            return getPathInSameRow(startPosition, endPosition, maxPosition);
        }
        if (startPosition.isSameColumn(endPosition)) {
            return getPathInSameColumn(startPosition, endPosition, maxPosition);
        }

        throw new IllegalArgumentException(MoveRule.INVALID_POSITION);
    }

    @Override
    public boolean isAbleToThrough(Path legalPath, List<Piece> piecesOnBoard, Team team) {
        Optional<Piece> collisionPiece = findFirstPieceOnRoute(legalPath, piecesOnBoard);
        if (collisionPiece.isEmpty()) {
            return true;
        }

        Position destination = legalPath.getLast();
        if (!collisionPiece.get().isSamePosition(destination)) {
            return false;
        }

        if (collisionPiece.get().isSameTeam(team)) {
            return false;
        }

        return true;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.CHARIOT;
    }

    private Path getPathInSameRow(Position startPosition, Position endPosition, Position maxPosition) {
        if (maxPosition.equals(startPosition)) {
            return generateStraightRoute(startPosition, endPosition, new RelativePosition(0, -1));
        }
        return generateStraightRoute(startPosition, endPosition, new RelativePosition(0, 1));
    }

    private Path getPathInSameColumn(Position startPosition, Position endPosition, Position maxPosition) {
        if (maxPosition.equals(startPosition)) {
            return generateStraightRoute(startPosition, endPosition, new RelativePosition(-1, 0));
        }
        return generateStraightRoute(startPosition, endPosition, new RelativePosition(1, 0));
    }

    private Path generateStraightRoute(Position minPosition, Position maxPosition, RelativePosition direction) {
        List<Position> path = new ArrayList<>();

        while (!minPosition.equals(maxPosition)) {
            minPosition = minPosition.apply(direction);
            path.add(minPosition);
        }
        return new Path(path);
    }
}
