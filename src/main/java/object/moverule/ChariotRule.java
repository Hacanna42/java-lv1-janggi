package object.moverule;

import java.util.ArrayList;
import java.util.List;
import object.Coordinate;
import object.Route;
import object.piece.Piece;
import object.piece.PieceType;
import object.piece.Team;

public class ChariotRule implements MoveRule {

    @Override
    public Route getLegalRoute(Coordinate startCoordinate, Coordinate endCoordinate, Team team) {
        Coordinate minCoordinate = Coordinate.getMinCoordinate(startCoordinate, endCoordinate);
        Coordinate maxCoordinate = Coordinate.getMaxCoordinate(startCoordinate, endCoordinate);

        List<Coordinate> coordinates = new ArrayList<>();
        if (startCoordinate.isSameColumn(endCoordinate)) {
            return calculateLegalRoute(minCoordinate, maxCoordinate, coordinates, new Coordinate(1, 0));
        }
        if (startCoordinate.isSameRow(endCoordinate)) {
            return calculateLegalRoute(minCoordinate, maxCoordinate, coordinates, new Coordinate(0, 1));
        }
        throw new IllegalArgumentException(MoveRule.INVALID_POSITION);
    }

    @Override
    public boolean isAbleToThrough(Route legalRoute, List<Piece> piecesOnBoard, Team team) {
        return false;
    }

    private static Route calculateLegalRoute(Coordinate minCoordinate, Coordinate maxCoordinate, List<Coordinate> coordinates,
                                             Coordinate direction) {
        while (!minCoordinate.equals(maxCoordinate)) {
            minCoordinate = minCoordinate.add(direction);
            coordinates.add(minCoordinate);
        }
        return new Route(coordinates);
    }

    public PieceType getPieceType() {
        return PieceType.CHARIOT;
    }
}
