package object.game.db;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameBoardDaoTest {
    /*
     * ! 주의 !
     * FakeConnectionGameBoardDao 를 사용하여 테스트할 것
     */
    private final TestConnectionGameBoardDao dao = new TestConnectionGameBoardDao();

    @BeforeEach
    void initializeTable() {
        if (dao.getClass() != TestConnectionGameBoardDao.class) {
            throw new RuntimeException("테스트 코드가 접근하는 DAO 클래스가 올바르지 않습니다. 프로덕션용 DAO를 사용하고 있지는 않은지 점검하세요.");
        }

        dao.deleteAllTestTable();
    }

    long createInitialGameSession() {
        GameBoardRecord gameBoardRecord = new GameBoardRecord("홍", "IN_PROGRESS");
        return dao.create(gameBoardRecord);
    }

    @DisplayName("DB 연결 테스트")
    @Test
    void isAbleToConnect() {
        // when
        boolean actual = dao.isAbleToConnect();

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @DisplayName("현재 진행중인 게임이 없으면 Session Id -1이 반환된다.")
    @Test
    void getActiveGameSessionId() {
        // when
        long sessionId = dao.getActiveGameSessionId();

        // then
        Assertions.assertThat(sessionId).isEqualTo(-1);
    }

    @DisplayName("GameBoardRecord로 새로운 세션을 생성할 수 있다. 생성과 동시에 sessionId가 반환된다.")
    @Test
    void create() {
        // given
        GameBoardRecord gameBoardRecord = new GameBoardRecord("홍", "IN_PROGRESS");

        // when
        long createdSessionId = dao.create(gameBoardRecord);

        // then
        Assertions.assertThat(createdSessionId).isEqualTo(1);
    }

    @DisplayName("현재 진행중인 게임의 Session Id를 반환할 수 있다.")
    @Test
    void getActiveSessionIdForInProgressGame() {
        // given
        long createdSessionId = createInitialGameSession();

        // when
        long actualSessionId = dao.getActiveGameSessionId();

        // then
        Assertions.assertThat(actualSessionId).isEqualTo(createdSessionId);
    }

    @DisplayName("현재 진행중인 게임의 정보를 업데이트 할 수 있다.")
    @Test
    void update() {
        // given
        long sessionId = createInitialGameSession();
        GameBoardRecord gameBoardRecord = new GameBoardRecord("청", "FINISHED");

        // when & then
        Assertions.assertThatNoException().isThrownBy(() ->
                dao.update(sessionId, gameBoardRecord));
    }

    @DisplayName("DB에 저장된 게임의 현재 차례를 가져올 수 있다.")
    @Test
    void readCurrentTurn() {
        // given
        long sessionId = createInitialGameSession();

        // when
        String actual = dao.readCurrentTurn(sessionId);

        // then
        Assertions.assertThat(actual).isEqualTo("홍");
    }
}
