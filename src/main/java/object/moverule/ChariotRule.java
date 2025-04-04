package object.moverule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import object.coordinate.Position;
import object.coordinate.Path;
import object.coordinate.RelativePosition;
import object.coordinate.palace.Adjacency;
import object.piece.Piece;
import object.piece.PieceType;
import object.piece.Team;

public class ChariotRule extends MoveRule {

    @Override
    public Path getLegalPath(Position fromPosition, Position toPosition, Team team) {
        Position maxPosition = Position.getAbsoluteBigPositionBetween(fromPosition, toPosition);

        if (fromPosition.isSameRow(toPosition)) {
            return getPathInSameRow(fromPosition, toPosition, maxPosition);
        }
        if (fromPosition.isSameColumn(toPosition)) {
            return getPathInSameColumn(fromPosition, toPosition, maxPosition);
        }

        Adjacency palaceAdjacency = Adjacency.generateOfPalaceArea();
        if (palaceAdjacency.isOnDiagonalLine(fromPosition, toPosition)) {
            return getDiagonalPath(fromPosition, toPosition);
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

    private Path getDiagonalPath(Position startPosition, Position endPosition) {
        int midX = (startPosition.getColumn() + endPosition.getRow()) / 2;
        int midY = (startPosition.getColumn() + endPosition.getRow()) / 2;
        Position middle = new Position(midX, midY);

        return new Path(List.of(middle, endPosition));
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
