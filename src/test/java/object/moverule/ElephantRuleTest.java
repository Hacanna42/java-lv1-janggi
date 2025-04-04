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

class ElephantRuleTest {
    @DisplayName("상은 목적지로 이동 가능한 올바른 경로를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"8, 3", "8, 7", "7, 2", "7, 8", "3, 2", "3, 8", "2, 3", "2, 7"})
    void elephantRouteTest(int toRow, int toColumn) {
        // given
        ElephantRule elephantRule = new ElephantRule();
        Position from = new Position(5, 5);
        Position to = new Position(toRow, toColumn);

        // when
        Path path = elephantRule.getLegalRoute(from, to, Team.BLUE);

        // then
        assertAll(
                () -> Assertions.assertThat(path.getSize()).isEqualTo(3)
        );
    }

    @DisplayName("상은 이동 가능한 경로가 없는 경우 예외를 발생시킨다.")
    @Test
    void elephantCantMoveExceptionTest() {
        // given
        ElephantRule elephantRule = new ElephantRule();

        // when
        Assertions.assertThatIllegalArgumentException().isThrownBy(() ->
                elephantRule.getLegalRoute(
                        new Position(0, 0),
                        new Position(1, 1),
                        Team.BLUE)
        );
    }

    @DisplayName("상은 도착지에 아군이 있으면 이동하지 못한다.")
    @Test
    void isAbleToThroughTest() {
        // given
        ElephantRule elephantRule = new ElephantRule();
        Piece fakeTeamPiece = new Piece(Team.BLUE, new ElephantRule(), new Position(8, 3));
        List<Piece> piecesOnBoard = List.of(fakeTeamPiece);

        // when
        Path path = new Path(List.of(new Position(6, 5), new Position(7, 3), new Position(8, 3)));
        boolean actual = elephantRule.isAbleToThrough(path, piecesOnBoard, Team.BLUE);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @DisplayName("상은 도착지에 적군이 있으면 이동 가능하다.")
    @Test
    void isAbleToThroughWhenEnemyTest() {
        // given
        ElephantRule elephantRule = new ElephantRule();
        Piece fakeTeamPiece = new Piece(Team.RED, new ElephantRule(), new Position(8, 3));
        List<Piece> piecesOnBoard = List.of(fakeTeamPiece);

        // when
        Path path = new Path(List.of(new Position(6, 5), new Position(7, 3), new Position(8, 3)));
        boolean actual = elephantRule.isAbleToThrough(path, piecesOnBoard, Team.BLUE);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @DisplayName("상은 도착지가 비어있으면 이동 가능하다.")
    @Test
    void isAbleToThroughWhenEmpty() {
        // given
        ElephantRule elephantRule = new ElephantRule();
        List<Piece> piecesOnBoard = List.of();

        // when
        Path path = new Path(List.of(new Position(6, 5), new Position(7, 3), new Position(8, 3)));
        boolean actual = elephantRule.isAbleToThrough(path, piecesOnBoard, Team.BLUE);

        // then
        Assertions.assertThat(actual).isTrue();
    }
}
