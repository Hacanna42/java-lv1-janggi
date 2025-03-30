package object.piece.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class FakeConnectionPieceDao extends PieceDao {
    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "test";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    @Override
    protected Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (SQLException exception) {
            return null;
        }
    }

    List<PieceRecord> readAllTestTable() {
        List<PieceRecord> pieceRecords = new ArrayList<>();
        final var query = "SELECT * FROM piece_state";
        try (var connection = getConnection();
        var prepareStatement = connection.prepareStatement(query)) {
            var resultSet = prepareStatement.executeQuery();
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

    void deleteAllTestTable() {
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
