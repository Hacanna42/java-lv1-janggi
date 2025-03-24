package object.moverule;

import java.util.List;
import java.util.Optional;
import object.Coordinate;
import object.Path;
import object.piece.Piece;
import object.piece.PieceType;
import object.piece.Team;

public class HorseRule implements MoveRule {

    private final List<Path> canMoveDirections = List.of(
            new Path(List.of(new Coordinate(1, 0), new Coordinate(1, -1))),
            new Path(List.of(new Coordinate(1, 0), new Coordinate(1, 1))),
            new Path(List.of(new Coordinate(-1, 0), new Coordinate(-1, -1))),
            new Path(List.of(new Coordinate(-1, 0), new Coordinate(-1, 1))),
            new Path(List.of(new Coordinate(0, 1), new Coordinate(1, 1))),
            new Path(List.of(new Coordinate(0, 1), new Coordinate(-1, 1))),
            new Path(List.of(new Coordinate(0, -1), new Coordinate(1, -1))),
            new Path(List.of(new Coordinate(0, -1), new Coordinate(-1, -1)))
    );

    @Override
    public Path getLegalRoute(Coordinate from, Coordinate to, Team team) {
        for (Path canMoveDirection : canMoveDirections) {
            if (from.add(canMoveDirection).equals(to)) {
                return canMoveDirection;
            }
        }

        throw new IllegalArgumentException(INVALID_POSITION);
    }

    @Override
    public boolean isAbleToThrough(Path legalPath, List<Piece> piecesOnBoard, Team team) {
        Coordinate destination = legalPath.getDestination();
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
