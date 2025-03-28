package object.moverule;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import object.coordinate.Position;
import object.piece.Piece;
import object.piece.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MoveRuleTest {
    @DisplayName("제자리로 이동하려고 시도하면, 예외를 발생시킨다.")
    @Test
    void moveToSamePlaceTest() {
        // given
        MoveRule moveRule = new SoldierRule();

        // when & then
        Assertions.assertThatIllegalArgumentException().isThrownBy(() ->
                moveRule.checkAbleToMove(new Position(0, 0),
                        new Position(0, 0),
                        List.of(),
                        Team.BLUE)
        );
    }

    @DisplayName("기물이 경로에 있는 기물을 통과할 수 없다면, 예외를 발생시킨다.")
    @Test
    void cannotThroughTest() {
        // given
        MoveRule moveRule = new SoldierRule();
        Piece fakePiece = new Piece(Team.RED, new SoldierRule(), new Position(1, 0));

        // when & then
        Assertions.assertThatIllegalArgumentException().isThrownBy(() ->
               moveRule.checkAbleToMove(
                       new Position(0, 0),
                       new Position(1, 0),
                       List.of(fakePiece),
                       Team.RED
               )
        );
    }

    @DisplayName("이동 가능하다면, 즉 올바른 경로가 있으며 경로의 기물을 통과할 수 있으면. 예외를 발생시키지 않는다.")
    @Test
    void noExceptionTest() {
        // given
        MoveRule moveRule = new SoldierRule();
        Piece fakePiece = new Piece(Team.BLUE, new SoldierRule(), new Position(1, 0));

        // when & then
        Assertions.assertThatNoException().isThrownBy(() ->
                moveRule.checkAbleToMove(
                        new Position(0, 0),
                        new Position(1, 0),
                        List.of(fakePiece),
                        Team.RED
                )
        );
    }
}
