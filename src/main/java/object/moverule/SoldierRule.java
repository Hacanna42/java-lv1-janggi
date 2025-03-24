package object.moverule;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import object.Coordinate;
import object.Route;
import object.piece.Piece;
import object.piece.PieceType;
import object.piece.Team;

public class SoldierRule implements MoveRule {

    private static final Map<Team, List<Route>> teamCanMoveDirection;

    static {
        final List<Route> blueCanMoveDirections = List.of(
                new Route(List.of(new Coordinate(0, 1))),
                new Route(List.of(new Coordinate(0, -1))),
                new Route(List.of(new Coordinate(1, 0)))
        );
        final List<Route> redCanMoveDirections = List.of(
                new Route(List.of(new Coordinate(0, 1))),
                new Route(List.of(new Coordinate(0, -1))),
                new Route(List.of(new Coordinate(-1, 0)))
        );

        teamCanMoveDirection = Map.of(Team.BLUE, blueCanMoveDirections, Team.RED, redCanMoveDirections);
    }

    @Override
    public Route getLegalRoute(Coordinate fromCoordinate, Coordinate toCoordinate, Team team) {
        for (Route canMoveRoute : teamCanMoveDirection.get(team)) {
            if (fromCoordinate.add(canMoveRoute).equals(toCoordinate)) {
                return Route.makeAbsolute(fromCoordinate, canMoveRoute);
            }
        }

        throw new IllegalArgumentException(INVALID_POSITION);
    }

    @Override
    public boolean isAbleToThrough(Route route, List<Piece> piecesOnBoard, Team team) {
        Optional<Piece> piece = findFirstPieceOnRoute(route, piecesOnBoard);
        if (piece.isPresent()) {
            if (!piece.get().isSameCoordinate(route.getLast())) {
                return false;
            }
            if (piece.get().isSameTeam(team)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.SOLIDER;
    }
}
