package object.game.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import object.game.GameBoard;

public class GameBoardDao {
    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "jjangi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

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
            throw new SQLException("PK 생성 실패. 프로그램을 재시작 해주세요.");
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
            System.out.println("DB 연결 중 예기치 못한 문제가 발생했습니다. 더 이상 진행 상황이 저장되지 않습니다.");
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
            System.out.println("DB 연결 문제로 인해 현재 차례를 가져올 수 없습니다.");
            exception.printStackTrace();
        }

        throw new IllegalArgumentException("DB 연결 문제로 인해 현재 차례를 가져올 수 없습니다.");
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (SQLException exception) {
            System.out.println("DB에 연결할 수 없습니다.");
            exception.printStackTrace();
            return null;
        }
    }
}
