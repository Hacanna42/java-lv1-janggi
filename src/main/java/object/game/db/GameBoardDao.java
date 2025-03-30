package object.game.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GameBoardDao {
    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static final String CONNECTION_ERROR_MESSAGE = "DB 작업 중 예기치 못한 오류가 발생했습니다.";

    public boolean isAbleToConnect() {
        var connect = getConnection();
        return connect != null;
    }

    public int getActiveGameSessionId() {
        final var query = "SELECT id FROM game_session WHERE status = ?";

        try (final var connection = getConnection();
             final var prepareStatement = connection.prepareStatement(query)) {
            prepareStatement.setString(1, "IN_PROGRESS");
            ResultSet resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
            return -1;
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public long create(GameBoardRecord gameBoard) {
        final var query = "INSERT INTO game_session (current_turn, status) VALUES (?, ?)";

        try (final var connection = getConnection();
             final var prepareStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, gameBoard.currentTurn());
            prepareStatement.setString(2, gameBoard.status());

            prepareStatement.executeUpdate();
            ResultSet resultSet = prepareStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            throw new SQLException(CONNECTION_ERROR_MESSAGE);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void update(long gameSessionId, GameBoardRecord gameBoard) {
        final var query = "UPDATE game_session SET current_turn = ?, status = ? WHERE id = ?";

        try (final var connection = getConnection();
             final var prepareStatement = connection.prepareStatement(query)) {
            prepareStatement.setString(1, gameBoard.currentTurn());
            prepareStatement.setString(2, gameBoard.status());
            prepareStatement.setLong(3, gameSessionId);

            prepareStatement.execute();
        } catch (SQLException exception) {
            System.out.println(CONNECTION_ERROR_MESSAGE);
            exception.printStackTrace();
        }
    }

    public String readCurrentTurn(long gameSessionId) {
        final var query = "SELECT current_turn FROM game_session WHERE id = ?";

        try (final var connection = getConnection();
             final var prepareStatement = connection.prepareStatement(query)) {
            prepareStatement.setLong(1, gameSessionId);
            ResultSet resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("current_turn");
            }
        } catch (SQLException exception) {
            System.out.println(CONNECTION_ERROR_MESSAGE);
            exception.printStackTrace();
        }

        throw new IllegalArgumentException(CONNECTION_ERROR_MESSAGE);
    }

    protected Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (SQLException exception) {
            return null;
        }
    }
}
