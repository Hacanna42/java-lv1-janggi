package object.moverule;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import object.coordinate.Position;
import object.coordinate.Path;
import object.coordinate.RelativePosition;
import object.coordinate.RelativePath;
import object.piece.Piece;
import object.piece.PieceType;
import object.piece.Team;

public class SoldierRule extends MoveRule {

    private static final Map<Team, List<RelativePath>> pathsByTeam;

    static {
        final List<RelativePath> blueTeamPath = List.of(
                new RelativePath(List.of(new RelativePosition(0, 1))),
                new RelativePath(List.of(new RelativePosition(0, -1))),
                new RelativePath(List.of(new RelativePosition(-1, 0)))
        );
        final List<RelativePath> redTeamPath = List.of(
                new RelativePath(List.of(new RelativePosition(0, 1))),
                new RelativePath(List.of(new RelativePosition(0, -1))),
                new RelativePath(List.of(new RelativePosition(1, 0)))
        );

        pathsByTeam = Map.of(Team.BLUE, blueTeamPath, Team.RED, redTeamPath);
    }

    @Override
    public Path getLegalPath(Position fromPosition, Position toPosition, Team team) {
        for (RelativePath relativePath : pathsByTeam.get(team)) {
            try {
                if (fromPosition.apply(relativePath).equals(toPosition)) {
                    return relativePath.makeAbsolutePath(fromPosition);
                }
            } catch (IllegalStateException exception) {
                // 범위 밖의 경로 가지치기
                continue;
            }
        }

        throw new IllegalArgumentException(INVALID_POSITION);
    }

    @Override
    public boolean isAbleToThrough(Path path, List<Piece> piecesOnBoard, Team team) {
        Optional<Piece> piece = findFirstPieceOnRoute(path, piecesOnBoard);
        if (piece.isPresent()) {
            if (!piece.get().isSamePosition(path.getLast())) {
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
        return PieceType.SOLDIER;
    }
}
