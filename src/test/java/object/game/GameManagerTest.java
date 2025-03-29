package object.game;

import org.junit.jupiter.api.DisplayName;

class GameManagerTest {
    /*
     * 개발하면서 사용한 테스트 코드,
     * 프로덕션 DB와 완전히 분리하여 테스트 코드를 작성할 방법을 찾아내지 못했음.
     * 의도 없이 실행 금지
     */

    @DisplayName("Active Session 없음")
//    @Test
    void getActiveSessionIdTest() {
        // given
        GameManager gameManager = new GameManager();

        // when
//        int actualSessionId = gameSettingManager.getActiveGameSessionId();

        // then
//        Assertions.assertThat(actualSessionId).isEqualTo(-1);
    }

    @DisplayName("Active Session 있음")
//    @Test
    void getActiveSessionIdTest2() {
        // given
        GameManager gameManager = new GameManager();

        // when
//        int actualSessionId = gameSettingManager.getActiveGameSessionId();

        // then
//        Assertions.assertThat(actualSessionId).isEqualTo(1);
    }
}
