package object.moverule;

import java.util.List;
import object.Coordinate;
import object.Route;
import object.piece.Piece;
import object.piece.PieceType;
import object.piece.Team;

public class ElephantRule implements MoveRule {
    private final List<Route> canMoveDirections = List.of(
            new Route(List.of(new Coordinate(1, 0), new Coordinate(1, -1), new Coordinate(1, -1))),
            new Route(List.of(new Coordinate(1, 0), new Coordinate(1, 1), new Coordinate(1, 1))),
            new Route(List.of(new Coordinate(-1, 0), new Coordinate(-1, -1), new Coordinate(-1, -1))),
            new Route(List.of(new Coordinate(-1, 0), new Coordinate(-1, 1), new Coordinate(-1, 1))),
            new Route(List.of(new Coordinate(0, 1), new Coordinate(1, 1), new Coordinate(1, 1))),
            new Route(List.of(new Coordinate(0, 1), new Coordinate(-1, 1), new Coordinate(-1, 1))),
            new Route(List.of(new Coordinate(0, -1), new Coordinate(1, -1), new Coordinate(1, -1))),
            new Route(List.of(new Coordinate(0, -1), new Coordinate(-1, -1), new Coordinate(-1, -1)))
    );

    @Override
    public Route getLegalRoute(Coordinate startCoordinate, Coordinate endCoordinate, Team team) {
        return null;
    }

    @Override
    public boolean isAbleToThrough(Route legalRoute, List<Piece> piecesOnBoard, Team team) {
        return false;
    }

    public PieceType getPieceType() {
        return PieceType.ELEPHANT;
    }
}
