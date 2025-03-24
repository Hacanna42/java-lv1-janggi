package object.moverule;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import object.Coordinate;
import object.Route;
import object.piece.Piece;
import object.piece.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ChariotRuleTest {
    @DisplayName("차는 목적지로 이동 가능한 올바른 경로를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"5, 8", "5, 2", "8, 5"})
    void chariotRouteTest(int toRow, int toColumn) {
        // given
        ChariotRule chariotRule = new ChariotRule();
        Coordinate from = new Coordinate(5, 5);
        Coordinate to = new Coordinate(toRow, toColumn);

        // when
        Route route = chariotRule.getLegalRoute(from, to, Team.BLUE);

        // then
        assertAll(
                () -> Assertions.assertThat(route.getSize()).isEqualTo(3),
                () -> Assertions.assertThat(route.getDestination()).isEqualTo(to)
        );
    }

    @DisplayName("차는 목적지를 제외한 이동 경로에 다른 기물이 있으면 이동하지 못한다.")
    @Test
    void chariotRouteCollisionTest() {
        // given
        ChariotRule chariotRule = new ChariotRule();
        Route legalRoute = new Route(List.of(
                new Coordinate(0, 1),
                new Coordinate(0, 2),
                new Coordinate(0, 3)
        ));
        List<Piece> piecesOnBoard = List.of(new Piece(null, null, new Coordinate(0, 2)));

        // when
        boolean actual = chariotRule.isAbleToThrough(legalRoute, piecesOnBoard, null);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @DisplayName("차는 도착 지점에 아군이 있으면 이동하지 못한다.")
    @Test
    void chariotDestinationOurTeamTest() {
        // given
        ChariotRule chariotRule = new ChariotRule();
        Route legalRoute = new Route(List.of(
                new Coordinate(0, 1),
                new Coordinate(0, 2),
                new Coordinate(0, 3)
        ));
        List<Piece> piecesOnBoard = List.of(new Piece(Team.BLUE, null, new Coordinate(0, 3)));

        // when
        boolean actual = chariotRule.isAbleToThrough(legalRoute, piecesOnBoard, Team.BLUE);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @DisplayName("차는 도착 지점에 적군이 있으면 이동할 수 있다.")
    @Test
    void chariotDestinationEnemyTest() {
        // given
        ChariotRule chariotRule = new ChariotRule();
        Route legalRoute = new Route(List.of(
                new Coordinate(0, 1),
                new Coordinate(0, 2),
                new Coordinate(0, 3)
        ));
        List<Piece> piecesOnBoard = List.of(new Piece(Team.RED, null, new Coordinate(0, 3)));

        // when
        boolean actual = chariotRule.isAbleToThrough(legalRoute, piecesOnBoard, Team.BLUE);

        // then
        Assertions.assertThat(actual).isTrue();
    }
}
