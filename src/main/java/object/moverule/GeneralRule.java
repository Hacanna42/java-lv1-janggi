package object.moverule;

import java.util.List;
import java.util.Optional;
import object.coordinate.Position;
import object.coordinate.Path;
import object.coordinate.palace.Adjacency;
import object.piece.Piece;
import object.piece.PieceType;
import object.piece.Team;

public class GeneralRule extends MoveRule {

    @Override
    public Path getLegalPath(Position fromPosition, Position toPosition, Team team) {
        Adjacency palaceAdjacency = Adjacency.generateOfPalaceArea(team);
        if (!palaceAdjacency.isConnected(fromPosition, toPosition)) {
            throw new IllegalArgumentException(INVALID_POSITION);
        }
        return new Path(List.of(toPosition));
    }

    @Override
    public boolean isAbleToThrough(Path legalPath, List<Piece> piecesOnBoard, Team team) {
        // TODO: 일급 컬렉션으로 개선 (목적지의 기물 가져오기.. 등의 동작이 제공되면 편하겠음)
        Optional<Piece> collisionPiece = findFirstPieceOnRoute(legalPath, piecesOnBoard);
        if (collisionPiece.isEmpty()) {
            return true;
        }
        if (collisionPiece.get().isSameTeam(team)) {
            return false;
        }

        return true;
    }


    public PieceType getPieceType() {
        return PieceType.GENERAL;
    }
}
