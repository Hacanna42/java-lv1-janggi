package object.piece.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieceDao {
    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "jjangi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public void updateAll(long gameSessionId, List<PieceRecord> pieces) {
        final var deleteQuery = "DELETE FROM piece_state WHERE game_session_id = ?";
        final var insertQuery = "INSERT INTO piece_state (game_session_id, type, team, position_row, position_column) VALUES (?, ?, ?, ?, ?)";

        Connection connection = getConnection();
        if (connection == null) {
            System.out.println("DB에 연결할 수 없습니다. 더 이상 진행 상황이 저장되지 않습니다.");
            return;
        }

        try {
            connection.setAutoCommit(false);

            try (var deleteStmt = connection.prepareStatement(deleteQuery);
                 var insertStmt = connection.prepareStatement(insertQuery)) {

                deleteStmt.setLong(1, gameSessionId);
                deleteStmt.executeUpdate();

                for (PieceRecord piece : pieces) {
                    insertStmt.setLong(1, gameSessionId);
                    insertStmt.setString(2, piece.type());
                    insertStmt.setString(3, piece.team());
                    insertStmt.setInt(4, piece.positionRow());
                    insertStmt.setInt(5, piece.positionColumn());
                    insertStmt.addBatch();
                }

                insertStmt.executeBatch();
                connection.commit();
            }

        } catch (SQLException exception) {
            System.out.println("DB에 기물 정보를 업데이트하다가 예기치 못한 오류가 발생했습니다.");
            exception.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                connection.close(); // 명시적 닫기
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

    public List<PieceRecord> readAll(long gameSessionId) {
        final var query = "SELECT * FROM piece_state WHERE game_session_id = ?";
        List<PieceRecord> pieces = new ArrayList<>();

        try (var connection = getConnection();
             var prepareStatement = connection.prepareStatement(query)) {
            prepareStatement.setLong(1, gameSessionId);
            ResultSet resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                pieces.add(PieceRecord.from(resultSet));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return pieces;
    }


    private Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME,
                    PASSWORD);
        } catch (SQLException exception) {
            System.out.println("DB에 연결할 수 없습니다.");
            return null;
        }
    }
}
