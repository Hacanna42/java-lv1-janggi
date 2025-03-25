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
        Position maxPosition = Position.getMaxCoordinate(startPosition, endPosition);

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

    private Path generateStraightRoute(Position minPosition, Position maxPosition, RelativePosition direction) {
        List<Position> footPrints = new ArrayList<>();

        while (!minPosition.equals(maxPosition)) {
            minPosition = minPosition.add(direction);
            footPrints.add(minPosition);
        }
        return new Path(footPrints);
    }

    public PieceType getPieceType() {
        return PieceType.CHARIOT;
    }
}
