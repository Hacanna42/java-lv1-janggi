package object.moverule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import object.Coordinate;
import object.Path;
import object.piece.Piece;
import object.piece.PieceType;
import object.piece.Team;

public class ChariotRule implements MoveRule {

    @Override
    public Path getLegalRoute(Coordinate startCoordinate, Coordinate endCoordinate, Team team) {
        Coordinate maxCoordinate = Coordinate.getMaxCoordinate(startCoordinate, endCoordinate);

        if (startCoordinate.isSameColumn(endCoordinate)) {
            if (maxCoordinate.equals(startCoordinate)) {
                return generateStraightRoute(startCoordinate, endCoordinate, new Coordinate(-1, 0));
            }
            return generateStraightRoute(startCoordinate, endCoordinate, new Coordinate(1, 0));
        }

        if (startCoordinate.isSameRow(endCoordinate)) {
            if (maxCoordinate.equals(startCoordinate)) {
                return generateStraightRoute(startCoordinate, endCoordinate, new Coordinate(0, -1));
            }
            return generateStraightRoute(startCoordinate, endCoordinate, new Coordinate(0, 1));
        }

        throw new IllegalArgumentException(MoveRule.INVALID_POSITION);
    }

    @Override
    public boolean isAbleToThrough(Path legalPath, List<Piece> piecesOnBoard, Team team) {
        Optional<Piece> collisionPiece = findFirstPieceOnRoute(legalPath, piecesOnBoard);
        if (collisionPiece.isEmpty()) {
            return true;
        }

        Coordinate destination = legalPath.getDestination();
        if (!collisionPiece.get().isSameCoordinate(destination)) {
            return false;
        }

        if (collisionPiece.get().isSameTeam(team)) {
            return false;
        }

        return true;
    }

    private Path generateStraightRoute(Coordinate minCoordinate, Coordinate maxCoordinate, Coordinate direction) {
        List<Coordinate> footPrints = new ArrayList<>();

        while (!minCoordinate.equals(maxCoordinate)) {
            minCoordinate = minCoordinate.add(direction);
            footPrints.add(minCoordinate);
        }
        return new Path(footPrints);
    }

    public PieceType getPieceType() {
        return PieceType.CHARIOT;
    }
}
