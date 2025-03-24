package object.piece;

import java.util.List;
import java.util.Objects;
import object.Coordinate;
import object.Route;
import object.moverule.MoveRule;

public class Piece {

    private final Team team;
    private final MoveRule moveRule;
    private final Coordinate coordinate;

    public Piece(Team team, MoveRule moveRule, Coordinate coordinate) {
        this.team = team;
        this.moveRule = moveRule;
        this.coordinate = coordinate;
    }

    public Piece move(Coordinate fromCoordinate, Coordinate toCoordinate, List<Piece> piecesOnBoard) {
        moveRule.checkAbleToMove(fromCoordinate, toCoordinate, piecesOnBoard, team);
        return new Piece(this.team, this.moveRule, toCoordinate);
    }

    public Route getLegalRoute(Coordinate destination) {
        return moveRule.getLegalRoute(this.coordinate, destination, team);
    }

    public boolean isSameTeam(Team moveTeam) {
        return team.equals(moveTeam);
    }

    public boolean isSameTeam(Piece comparePiece) {
        return isSameTeam(comparePiece.team);
    }

    public boolean isSameCoordinate(Coordinate destination) {
        return coordinate.equals(destination);
    }

    public boolean isSameCoordinate(Piece comparePiece) {
        return isSameCoordinate(comparePiece.coordinate);
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

    public Coordinate getCoordinate() {
        return coordinate;
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
                && Objects.equals(coordinate, piece.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTeam(), moveRule, coordinate);
    }
}
