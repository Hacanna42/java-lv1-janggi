package object.moverule;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import object.Coordinate;
import object.Path;
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
        Coordinate from = new Coordinate(5, 5);
        Coordinate to = new Coordinate(toRow, toColumn);

        // when
        Path path = elephantRule.getLegalRoute(from, to, Team.BLUE);

        // then
        assertAll(
                () -> Assertions.assertThat(path.getSize()).isEqualTo(3)
        );
    }

    @DisplayName("상은 도착지에 아군이 있으면 이동하지 못한다.")
    @Test
    void isAbleToThroughTest() {
        // given
        ElephantRule elephantRule = new ElephantRule();
        Piece fakeTeamPiece = new Piece(Team.BLUE, new ElephantRule(), new Coordinate(8, 3));
        List<Piece> piecesOnBoard = List.of(fakeTeamPiece);

        // when
        Path path = new Path(List.of(new Coordinate(6, 5), new Coordinate(7, 3), new Coordinate(8, 3)));
        boolean actual = elephantRule.isAbleToThrough(path, piecesOnBoard, Team.BLUE);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @DisplayName("상은 도착지에 적군이 있으면 이동 가능하다.")
    @Test
    void isAbleToThroughWhenEnemyTest() {
        // given
        ElephantRule elephantRule = new ElephantRule();
        Piece fakeTeamPiece = new Piece(Team.RED, new ElephantRule(), new Coordinate(8, 3));
        List<Piece> piecesOnBoard = List.of(fakeTeamPiece);

        // when
        Path path = new Path(List.of(new Coordinate(6, 5), new Coordinate(7, 3), new Coordinate(8, 3)));
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
        Path path = new Path(List.of(new Coordinate(6, 5), new Coordinate(7, 3), new Coordinate(8, 3)));
        boolean actual = elephantRule.isAbleToThrough(path, piecesOnBoard, Team.BLUE);

        // then
        Assertions.assertThat(actual).isTrue();
    }
}
