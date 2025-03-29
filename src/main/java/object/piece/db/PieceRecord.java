package object.piece.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import object.game.GameBoard;
import object.piece.Piece;

public record PieceRecord(String type, String team, int positionRow, int positionColumn) {

    public static PieceRecord from(ResultSet rs) throws SQLException {
        return new PieceRecord(
                rs.getString("type"),
                rs.getString("team"),
                rs.getInt("position_row"),
                rs.getInt("position_column")
        );
    }

    public static List<PieceRecord> makeRecordsFrom(GameBoard gameBoard) {
        List<PieceRecord> records = new ArrayList<>();
        for (Piece piece : gameBoard.getPieces()) {
            PieceRecord currentPieceRecord = new PieceRecord(
                    piece.getPieceType().getName(),
                    piece.getTeam().getName(),
                    piece.getRow(),
                    piece.getColumn()
            );

            records.add(currentPieceRecord);
        }

        return records;
    }
}
