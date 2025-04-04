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

public class CannonRule extends MoveRule {

    @Override
    public Path getLegalPath(Position fromPosition, Position toPosition, Team team) {
        if (fromPosition.isSameRow(toPosition)) {
            return getPathInSameRow(fromPosition, toPosition);
        }
        if (fromPosition.isSameColumn(toPosition)) {
            return getPathInSameColumn(fromPosition, toPosition);
        }

        Adjacency palaceAdjacency = Adjacency.generateOfPalaceArea();
        if (palaceAdjacency.isOnDiagonalLine(fromPosition, toPosition)) {
            return getDiagonalPath(fromPosition, toPosition);
        }

        throw new IllegalArgumentException(MoveRule.INVALID_POSITION);
    }

    @Override
    public boolean isAbleToThrough(Path legalPath, List<Piece> piecesOnBoard, Team team) {
        // 규칙: 포는 목적지를 포함하지 않은 이동 경로에 무조건 포가 아닌 기물과 1회 충돌해야 함.
        Position destination = legalPath.getLast();

        List<Piece> piecesOnRoute = piecesOnBoard.stream()
                .filter(piece -> legalPath.contains(piece.getPosition()))
                .filter(piece -> !piece.isSamePosition(destination))
                .toList();

        if (piecesOnRoute.size() != 1) {
            return false;
        }

        if (piecesOnRoute.getFirst().isSameType(getPieceType())) {
            return false;
        }

        Optional<Piece> pieceOnDestination = piecesOnBoard.stream()
                .filter(piece -> piece.isSamePosition(destination))
                .findFirst();

        if (pieceOnDestination.isPresent() && pieceOnDestination.get().isSameTeam(team)) {
            return false;
        }

        if (pieceOnDestination.isPresent() && pieceOnDestination.get().isSameType(getPieceType())) {
            return false;
        }

        return true;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.CANNON;
    }

    private Path getDiagonalPath(Position startPosition, Position endPosition) {
        int midX = (startPosition.getColumn() + endPosition.getRow()) / 2;
        int midY = (startPosition.getColumn() + endPosition.getRow()) / 2;
        Position middle = new Position(midX, midY);

        return new Path(List.of(middle, endPosition));
    }

    private Path getPathInSameRow(Position startPosition, Position endPosition) {
        Position maxPosition = Position.getAbsoluteBigPositionBetween(startPosition, endPosition);
        if (maxPosition.equals(startPosition)) {
            return generateStraightRoute(startPosition, endPosition, RelativePosition.left());
        }
        return generateStraightRoute(startPosition, endPosition, RelativePosition.right());
    }

    private Path getPathInSameColumn(Position startPosition, Position endPosition) {
        Position maxPosition = Position.getAbsoluteBigPositionBetween(startPosition, endPosition);
        if (maxPosition.equals(startPosition)) {
            return generateStraightRoute(startPosition, endPosition, RelativePosition.up());
        }
        return generateStraightRoute(startPosition, endPosition, RelativePosition.down());
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
