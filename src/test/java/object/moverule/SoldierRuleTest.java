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

class SoldierRuleTest {
    @DisplayName("졸은 목적지로 이동 가능한 올바른 경로를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"-1, 0", "0, -1", "0, 1"})
    void soldierRouteTestForBlue(int toRow, int toColumn) {
        // given
        SoldierRule soldierRule = new SoldierRule();
        Coordinate from = new Coordinate(0, 0);
        Coordinate to = new Coordinate(toRow, toColumn);

        // when
        Path path = soldierRule.getLegalRoute(from, to, Team.BLUE);

        // then
        assertAll(
                () -> Assertions.assertThat(path.getSize()).isEqualTo(1),
                () -> Assertions.assertThat(path.getDestination()).isEqualTo(to)
        );
    }

    @DisplayName("졸은 홍팀일 때, 위로 이동이 불가능한 대신 아래로 이동할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"1, 0", "0, -1", "0, 1"})
    void soldierStraightMoveTestForRed(int toRow, int toColumn) {
        // given
        SoldierRule soldierRule = new SoldierRule();
        Coordinate from = new Coordinate(0, 0);
        Coordinate to = new Coordinate(toRow, toColumn);

        // when
        Path path = soldierRule.getLegalRoute(from, to, Team.RED);

        // then
        assertAll(
                () -> Assertions.assertThat(path.getSize()).isEqualTo(1),
                () -> Assertions.assertThat(path.getDestination()).isEqualTo(to)
        );
    }

    @DisplayName("졸은 도착지에 아군이 있으면 이동하지 못한다.")
    @Test
    void isAbleToThroughTest() {
        // given
        SoldierRule soldierRule = new SoldierRule();
        Piece fakeTeamPiece = new Piece(Team.BLUE, new SoldierRule(), new Coordinate(1, 0));
        List<Piece> piecesOnBoard = List.of(fakeTeamPiece);

        // when
        Path path = new Path(List.of(new Coordinate(1, 0)));
        boolean actual = soldierRule.isAbleToThrough(path, piecesOnBoard, Team.BLUE);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @DisplayName("졸은 도착지에 적군이 있으면 이동 가능하다.")
    @Test
    void isAbleToThroughWhenEnemyTest() {
        // given
        SoldierRule soldierRule = new SoldierRule();
        Piece fakeTeamPiece = new Piece(Team.RED, new SoldierRule(), new Coordinate(1, 0));
        List<Piece> piecesOnBoard = List.of(fakeTeamPiece);

        // when
        Path path = new Path(List.of(new Coordinate(1, 0)));
        boolean actual = soldierRule.isAbleToThrough(path, piecesOnBoard, Team.BLUE);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @DisplayName("졸은 도착지가 비어있으면 이동 가능하다.")
    @Test
    void isAbleToThroughWhenEmpty() {
        // given
        SoldierRule soldierRule = new SoldierRule();
        List<Piece> piecesOnBoard = List.of();

        // when
        Path path = new Path(List.of(new Coordinate(1, 0)));
        boolean actual = soldierRule.isAbleToThrough(path, piecesOnBoard, Team.BLUE);

        // then
        Assertions.assertThat(actual).isTrue();
    }
}
