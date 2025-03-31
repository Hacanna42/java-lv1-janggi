package object.game.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import object.db.TestDBConnector;

public class TestConnectionGameBoardDao extends GameBoardDao {
    public TestConnectionGameBoardDao() {
        super(new TestDBConnector());
    }

    public List<GameBoardRecord> readAll() {
        List<GameBoardRecord> records = new ArrayList<>();
        final var query = "SELECT * FROM game_session";
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                GameBoardRecord record = new GameBoardRecord(
                        resultSet.getString("current_turn"),
                        resultSet.getString("status")
                );
                records.add(record);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return records;
    }

    public void deleteAllTestTable() {
        final var deleteQuery = "DELETE FROM game_session;";
        final var resetAutoIncrementQuery = "ALTER TABLE game_session AUTO_INCREMENT = 1";

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
