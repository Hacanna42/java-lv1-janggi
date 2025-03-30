package object.game.db;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import object.game.GameBoard;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameBoardRecordTest {
    @DisplayName("GameBoard의 정보를 통해 GameBoardRecord를 생성할 수 있다.")
    @Test
    void from() {
        // given
        GameBoard gameBoard = GameBoard.generateToInitGameFormat();

        // when
        GameBoardRecord gameBoardRecord = GameBoardRecord.from(gameBoard);

        // then
        assertAll(
                () -> Assertions.assertThat(gameBoardRecord.currentTurn()).isEqualTo("청"),
                () -> Assertions.assertThat(gameBoardRecord.status()).isEqualTo("IN_PROGRESS")
        );
    }

    @DisplayName("GameBoard의 게임이 진행 불가능하면, status는 'FINISHED'가 된다.")
    @Test
    void from2() {
        // given
        GameBoard gameBoard = new GameBoard(List.of());

        // when
        GameBoardRecord gameBoardRecord = GameBoardRecord.from(gameBoard);

        // then
        assertAll(
                () -> Assertions.assertThat(gameBoardRecord.currentTurn()).isEqualTo("청"),
                () -> Assertions.assertThat(gameBoardRecord.status()).isEqualTo("FINISHED")
        );
    }
}
