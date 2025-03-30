package object.game.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class FakeConnectionGameBoardDao extends GameBoardDao {
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

    void deleteAllTestTable() {
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
