package object.moverule;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import object.coordinate.Position;
import object.coordinate.Path;
import object.piece.Piece;
import object.piece.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HorseRuleTest {
    @DisplayName("마는 목적지로 이동 가능한 올바른 경로를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"7, 6", "7, 4", "3, 6", "3, 4", "6, 3", "6, 7", "4, 3", "4, 7"})
    void horseRouteTest(int toRow, int toColumn) {
        // given
        HorseRule horseRule = new HorseRule();
        Position from = new Position(5, 5);
        Position to = new Position(toRow, toColumn);

        // when
        Path path = horseRule.getLegalRoute(from, to, Team.BLUE);

        // then
        assertAll(
                () -> Assertions.assertThat(path.getSize()).isEqualTo(2)
        );
    }

    @DisplayName("마는 이동 가능한 경로가 없는 경우 예외를 발생시킨다.")
    @Test
    void horseCantMoveExceptionTest() {
        // given
        HorseRule horseRule = new HorseRule();

        // when
        Assertions.assertThatIllegalArgumentException().isThrownBy(() ->
                horseRule.getLegalRoute(
                        new Position(0, 0),
                        new Position(1, 1),
                        Team.BLUE)
        );
    }

    @DisplayName("마는 도착지에 아군이 있으면 이동하지 못한다.")
    @Test
    void isAbleToThroughTest() {
        // given
        HorseRule horseRule = new HorseRule();
        Piece fakeTeamPiece = new Piece(Team.BLUE, new HorseRule(), new Position(7, 6));
        List<Piece> piecesOnBoard = List.of(fakeTeamPiece);

        // when
        Path path = new Path(List.of(new Position(6, 5), new Position(7, 6)));
        boolean actual = horseRule.isAbleToThrough(path, piecesOnBoard, Team.BLUE);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @DisplayName("마는 도착지에 적군이 있으면 이동 가능하다.")
    @Test
    void isAbleToThroughWhenEnemyTest() {
        // given
        HorseRule horseRule = new HorseRule();
        Piece fakeTeamPiece = new Piece(Team.RED, new HorseRule(), new Position(7, 6));
        List<Piece> piecesOnBoard = List.of(fakeTeamPiece);

        // when
        Path path = new Path(List.of(new Position(6, 5), new Position(7, 6)));
        boolean actual = horseRule.isAbleToThrough(path, piecesOnBoard, Team.BLUE);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @DisplayName("마는 도착지가 비어있으면 이동 가능하다.")
    @Test
    void isAbleToThroughWhenEmpty() {
        // given
        HorseRule horseRule = new HorseRule();
        List<Piece> piecesOnBoard = List.of();

        // when
        Path path = new Path(List.of(new Position(6, 5), new Position(7, 6)));
        boolean actual = horseRule.isAbleToThrough(path, piecesOnBoard, Team.BLUE);

        // then
        Assertions.assertThat(actual).isTrue();
    }
}
