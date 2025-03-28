package object.piece;

import java.util.List;
import java.util.Objects;
import object.coordinate.Position;
import object.moverule.MoveRule;

public class Piece {

    private final Team team;
    private final MoveRule moveRule;
    private final Position position;

    public Piece(Team team, MoveRule moveRule, Position position) {
        this.team = team;
        this.moveRule = moveRule;
        this.position = position;
    }

    public Piece move(Position from, Position to, List<Piece> piecesOnBoard) {
        moveRule.checkAbleToMove(from, to, piecesOnBoard, team);
        return new Piece(this.team, this.moveRule, to);
    }

    public boolean isSameTeam(Team moveTeam) {
        return team.equals(moveTeam);
    }

    public boolean isSameTeam(Piece comparePiece) {
        return isSameTeam(comparePiece.team);
    }

    public boolean isSamePosition(Position position) {
        return this.position.equals(position);
    }

    public boolean isSamePosition(Piece comparePiece) {
        return isSamePosition(comparePiece.position);
    }

    public boolean isSameType(PieceType comparePieceType) {
        return moveRule.getPieceType().equals(comparePieceType);
    }

    public Team getTeam() {
        return team;
    }

    public PieceType getPieceType() {
        return moveRule.getPieceType();
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return getTeam() == piece.getTeam() && Objects.equals(moveRule, piece.moveRule)
                && Objects.equals(getPosition(), piece.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTeam(), moveRule, getPosition());
    }
}
