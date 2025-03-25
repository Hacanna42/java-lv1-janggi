package object.moverule;

import java.util.List;
import java.util.Optional;
import object.coordinate.Position;
import object.coordinate.Path;
import object.coordinate.RelativePath;
import object.coordinate.RelativePosition;
import object.piece.Piece;
import object.piece.PieceType;
import object.piece.Team;

public class ElephantRule extends MoveRule {
    private static final List<RelativePath> ablePaths = List.of(
            new RelativePath(List.of(new RelativePosition(1, 0), new RelativePosition(1, -1), new RelativePosition(1, -1))),
            new RelativePath(List.of(new RelativePosition(1, 0), new RelativePosition(1, 1), new RelativePosition(1, 1))),
            new RelativePath(List.of(new RelativePosition(-1, 0), new RelativePosition(-1, -1), new RelativePosition(-1, -1))),
            new RelativePath(List.of(new RelativePosition(-1, 0), new RelativePosition(-1, 1), new RelativePosition(-1, 1))),
            new RelativePath(List.of(new RelativePosition(0, 1), new RelativePosition(1, 1), new RelativePosition(1, 1))),
            new RelativePath(List.of(new RelativePosition(0, 1), new RelativePosition(-1, 1), new RelativePosition(-1, 1))),
            new RelativePath(List.of(new RelativePosition(0, -1), new RelativePosition(1, -1), new RelativePosition(1, -1))),
            new RelativePath(List.of(new RelativePosition(0, -1), new RelativePosition(-1, -1), new RelativePosition(-1, -1)))
    );

    @Override
    public Path getLegalRoute(Position from, Position to, Team team) {
        for (RelativePath relativePath : ablePaths) {
            try {
                if (from.add(relativePath).equals(to)) {
                    return relativePath.makeAbsolutePath(from);
                }
            } catch (IllegalStateException exception) {
                // 범위 밖의 경로 가지치기
                continue;
            }
        }

        throw new IllegalArgumentException(INVALID_POSITION);
    }

    @Override
    public boolean isAbleToThrough(Path legalPath, List<Piece> piecesOnBoard, Team team) {
        Position destination = legalPath.getLast();
        Optional<Piece> pieceOnDestination = piecesOnBoard.stream()
                .filter(piece -> piece.isSamePosition(destination))
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
        return PieceType.ELEPHANT;
    }
}
