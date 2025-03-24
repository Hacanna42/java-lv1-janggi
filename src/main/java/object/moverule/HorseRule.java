package object.moverule;

import java.util.List;
import java.util.Optional;
import object.Coordinate;
import object.Route;
import object.piece.Piece;
import object.piece.PieceType;
import object.piece.Team;

public class HorseRule implements MoveRule {

    private final List<Route> canMoveDirections = List.of(
            new Route(List.of(new Coordinate(1, 0), new Coordinate(1, -1))),
            new Route(List.of(new Coordinate(1, 0), new Coordinate(1, 1))),
            new Route(List.of(new Coordinate(-1, 0), new Coordinate(-1, -1))),
            new Route(List.of(new Coordinate(-1, 0), new Coordinate(-1, 1))),
            new Route(List.of(new Coordinate(0, 1), new Coordinate(1, 1))),
            new Route(List.of(new Coordinate(0, 1), new Coordinate(-1, 1))),
            new Route(List.of(new Coordinate(0, -1), new Coordinate(1, 1))),
            new Route(List.of(new Coordinate(0, -1), new Coordinate(-1, -1)))
    );

    @Override
    public Route getLegalRoute(Coordinate fromCoordinate, Coordinate toCoordinate, Team team) {
        for (Route canMoveDirection : canMoveDirections) {
            if (fromCoordinate.add(canMoveDirection).equals(toCoordinate)) {
                return canMoveDirection;
            }
        }

        throw new IllegalArgumentException(INVALID_POSITION);
    }

    @Override
    public boolean isAbleToThrough(Route legalRoute, List<Piece> piecesOnBoard, Team team) {
        Coordinate destination = legalRoute.getLast();
        Optional<Piece> pieceOnDestination = piecesOnBoard.stream()
                .filter(piece -> piece.isSameCoordinate(destination))
                .findFirst();

        if (pieceOnDestination.isEmpty()) {
            return true;
        }
        if (!pieceOnDestination.get().isSameTeam(team)) {
            return true;
        }

        return false;
    }

    public PieceType getPieceType() {
        return PieceType.HORSE;
    }
}
