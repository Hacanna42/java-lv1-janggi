package object.game;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import object.db.EmptyConnector;
import object.game.db.GameBoardRecord;
import object.game.db.TestConnectionGameBoardDao;
import object.game.db.GameBoardDao;
import object.piece.Team;
import object.piece.db.TestConnectionPieceDao;
import object.piece.db.PieceDao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameBoardSyncManagerTest {
    private final TestConnectionGameBoardDao testGameBoardDao = new TestConnectionGameBoardDao();
    private final TestConnectionPieceDao testPieceDao = new TestConnectionPieceDao();

    private final GameBoardSyncManager testSyncManager = new GameBoardSyncManager(testGameBoardDao, testPieceDao);

    @BeforeEach
    void initializeTable() {
        if (testGameBoardDao.getClass() != TestConnectionGameBoardDao.class || testPieceDao.getClass() != TestConnectionPieceDao.class) {
            throw new RuntimeException("테스트 코드가 접근하는 DAO 클래스가 올바르지 않습니다. 프로덕션용 DAO를 사용하고 있지는 않은지 점검하세요.");
        }

        testGameBoardDao.deleteAllTestTable();
        testPieceDao.deleteAllTestTable();
    }

    @DisplayName("DB 연결에 실패하면 로컬 게임을 시작한다.")
    @Test
    void loadLocalGame() {
        // given
        GameBoardSyncManager gameBoardSyncManager = new GameBoardSyncManager(new GameBoardDao(new EmptyConnector()), new PieceDao(new EmptyConnector()));

        // when
        GameBoard loadedGameBoard = gameBoardSyncManager.loadGameBoard();

        // then
        Assertions.assertThat(loadedGameBoard).isEqualTo(GameBoard.generateToInitGameFormat());
    }

    @DisplayName("DB 연결에 성공하면 온라인 게임을 시작하고, DB에 온라인 게임의 정보가 기록된다.")
    @Test
    void loadOnlineGame() {
        // when
        testSyncManager.loadGameBoard();

        // then
        List<GameBoardRecord> records = testGameBoardDao.readAll();
        assertAll(
                () -> Assertions.assertThat(records.size()).isEqualTo(1),
                () -> Assertions.assertThat(records.getFirst().currentTurn()).isEqualTo("청"),
                () -> Assertions.assertThat(records.getFirst().status()).isEqualTo("IN_PROGRESS")
        );
    }

    @DisplayName("이전에 진행중이던 게임이 있으면, 해당 게임을 불러온다.")
    @Test
    void loadOnlineGame2() {
        // given
        testSyncManager.loadGameBoard();
        long generatedSessionId = testGameBoardDao.getActiveGameSessionId();

        // when
        testSyncManager.loadGameBoard();
        long loadedSessionId = testGameBoardDao.getActiveGameSessionId();

        // then
        Assertions.assertThat(generatedSessionId).isEqualTo(loadedSessionId);
    }

    @DisplayName("게임 정보를 업데이트 할 수 있다.")
    @Test
    void updateGameSync() {
        // given
        GameBoard generatedGameBoard = testSyncManager.loadGameBoard();
        GameBoard fakeGameBoard = new GameBoard(List.of(), Team.RED);

        // when
        testSyncManager.updateGameSync(fakeGameBoard);

        // then
        List<GameBoardRecord> records = testGameBoardDao.readAll();
        String changedTurn = records.getFirst().currentTurn();
        assertAll(
                () -> Assertions.assertThat(generatedGameBoard.getCurrentTurn().getName()).isEqualTo("청"),
                () -> Assertions.assertThat(changedTurn).isEqualTo("홍")
        );
    }
}
