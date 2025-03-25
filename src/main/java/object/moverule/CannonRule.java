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

public class CannonRule extends MoveRule {

    @Override
    public Path getLegalRoute(Position startPosition, Position endPosition, Team team) {
        Position maxPosition = Position.getAbsoluteBigPositionBetween(startPosition, endPosition);

        if (startPosition.isSameColumn(endPosition)) {
            if (maxPosition.equals(startPosition)) {
                return generateStraightRoute(startPosition, endPosition, new RelativePosition(-1, 0));
            }
            return generateStraightRoute(startPosition, endPosition, new RelativePosition(1, 0));
        }

        if (startPosition.isSameRow(endPosition)) {
            if (maxPosition.equals(startPosition)) {
                return generateStraightRoute(startPosition, endPosition, new RelativePosition(0, -1));
            }
            return generateStraightRoute(startPosition, endPosition, new RelativePosition(0, 1));
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

        // 목적지에 기물이 있다면
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

    public PieceType getPieceType() {
        return PieceType.CANNON;
    }

    private Path generateStraightRoute(Position minPosition, Position maxPosition, RelativePosition direction) {
        List<Position> footPrints = new ArrayList<>();

        while (!minPosition.equals(maxPosition)) {
            minPosition = minPosition.add(direction);
            footPrints.add(minPosition);
        }
        return new Path(footPrints);
    }
}
