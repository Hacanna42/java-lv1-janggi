package object.piece.db;

import java.util.ArrayList;
import java.util.List;
import object.coordinate.Position;
import object.moverule.MoveRule;
import object.piece.Piece;
import object.piece.PieceType;
import object.piece.Team;

public class PieceMapper {
    public static List<Piece> generateGameBoardFrom(long gameSessionId) {
        List<Piece> pieces = new ArrayList<>();

        PieceDao pieceDao = new PieceDao();
        List<PieceRecord> pieceRecords = pieceDao.readAll(gameSessionId);
        for (PieceRecord pieceRecord : pieceRecords) {
            Team team = Team.from(pieceRecord.team());
            MoveRule moveRule = PieceType.from(pieceRecord.type()).createMoveRule();
            Position position = new Position(pieceRecord.positionRow(), pieceRecord.positionColumn());

            Piece piece = new Piece(team, moveRule, position);
            pieces.add(piece);
        }

        return pieces;
    }
}
