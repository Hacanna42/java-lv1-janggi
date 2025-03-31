package object.piece.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import object.db.TestDBConnector;

public class TestConnectionPieceDao extends PieceDao {

    public TestConnectionPieceDao() {
        super(new TestDBConnector());
    }

    List<PieceRecord> readAllTestTable() {
        List<PieceRecord> pieceRecords = new ArrayList<>();
        final var query = "SELECT * FROM piece_state";
        try (var connection = getConnection();
        var preparedStatement = connection.prepareStatement(query)) {
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final PieceRecord pieceRecord = new PieceRecord(
                        resultSet.getString("type"),
                        resultSet.getString("team"),
                        resultSet.getInt("position_row"),
                        resultSet.getInt("position_column")
                );
                pieceRecords.add(pieceRecord);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return pieceRecords;
    }

    public void deleteAllTestTable() {
        final var deleteQuery = "DELETE FROM piece_state;";
        final var resetAutoIncrementQuery = "ALTER TABLE piece_state AUTO_INCREMENT = 1";
        try (var connection = getConnection();
             var deleteStmt = connection.prepareStatement(deleteQuery);
             var resetStmt = connection.prepareStatement(resetAutoIncrementQuery)) {
            deleteStmt.executeUpdate();
            resetStmt.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
