package object.moverule;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import object.Coordinate;
import object.Path;
import object.piece.Piece;
import object.piece.PieceType;
import object.piece.Team;

public class SoldierRule extends MoveRule {

    private static final Map<Team, List<Path>> teamCanMoveDirection;

    static {
        final List<Path> blueCanMoveDirections = List.of(
                new Path(List.of(new Coordinate(0, 1))),
                new Path(List.of(new Coordinate(0, -1))),
                new Path(List.of(new Coordinate(-1, 0)))
        );
        final List<Path> redCanMoveDirections = List.of(
                new Path(List.of(new Coordinate(0, 1))),
                new Path(List.of(new Coordinate(0, -1))),
                new Path(List.of(new Coordinate(1, 0)))
        );

        teamCanMoveDirection = Map.of(Team.BLUE, blueCanMoveDirections, Team.RED, redCanMoveDirections);
    }

    @Override
    public Path getLegalRoute(Coordinate from, Coordinate to, Team team) {
        for (Path canMovePath : teamCanMoveDirection.get(team)) {
            if (from.add(canMovePath).equals(to)) {
                return Path.makeAbsolutePath(from, canMovePath);
            }
        }

        throw new IllegalArgumentException(INVALID_POSITION);
    }

    @Override
    public boolean isAbleToThrough(Path path, List<Piece> piecesOnBoard, Team team) {
        Optional<Piece> piece = findFirstPieceOnRoute(path, piecesOnBoard);
        if (piece.isPresent()) {
            if (!piece.get().isSameCoordinate(path.getLast())) {
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
