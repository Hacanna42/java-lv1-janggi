package piece;

import java.util.Objects;
import strategy.MoveStrategy;

public class Piece {

    private final MoveRule moveRule;
    private final Team team;
    private Position position;

    public Piece(Position position, MoveStrategy moveStrategy, PieceType pieceType, Team team) {
        this.position = position;
        this.moveRule = new MoveRule(moveStrategy, pieceType);
        this.team = team;
    }

    public Team team() {
        return team;
    }

    public void move(Pieces onRoutePieces) {
        this.position = moveRule.move(position, moveRule, team);
    }


    public boolean isSamePosition(Position destination) {
        return position.equals(destination);
    }

    public boolean isSamePosition(Piece comparePiece) {
        return isSamePosition(comparePiece.position);
    }

    public boolean isSameType(PieceType pieceType) {
        return moveRule.isSameType(pieceType);
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
        return Objects.equals(position, piece.position) && Objects.equals(moveRule, piece.moveRule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, moveRule);
    }

    public boolean isSameTeam(Team moveTeam) {
        return team.equals(moveTeam);
    }

    public boolean isSameTeam(Piece comparePiece) {
        return isSameTeam(comparePiece.team);
    }
}
