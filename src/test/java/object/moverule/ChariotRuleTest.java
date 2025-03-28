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

class ChariotRuleTest {
    @DisplayName("차는 목적지로 이동 가능한 올바른 경로를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"5, 5, 5, 8", "5, 5, 5, 2", "5, 5, 8, 5", "5, 5, 2, 5"})
    void chariotRouteTest(int fromRow, int fromCol, int toRow, int toCol) {
        // given
        ChariotRule chariotRule = new ChariotRule();
        Position from = new Position(fromRow, fromCol);
        Position to = new Position(toRow, toCol);

        // when
        Path path = chariotRule.getLegalPath(from, to, Team.BLUE);

        // then
        assertAll(
                () -> Assertions.assertThat(path.getSize()).isEqualTo(3),
                () -> Assertions.assertThat(path.getLast()).isEqualTo(to)
        );
    }

    @DisplayName("차는 목적지로 이동 가능한 경로가 없으면 예외를 발생 시킨다.")
    @Test
    void chariotRouteExceptionTest() {
        // given
        ChariotRule chariotRule = new ChariotRule();

        // when & then
        Assertions.assertThatIllegalArgumentException().isThrownBy(
                () -> chariotRule.getLegalPath(
                        new Position(0, 0),
                        new Position(1, 1),
                        Team.BLUE)
        );
    }

    @DisplayName("차는 목적지를 제외한 이동 경로에 다른 기물이 있으면 이동하지 못한다.")
    @Test
    void chariotRouteCollisionTest() {
        // given
        ChariotRule chariotRule = new ChariotRule();
        Path legalPath = new Path(List.of(
                new Position(0, 1),
                new Position(0, 2),
                new Position(0, 3)
        ));
        List<Piece> piecesOnBoard = List.of(new Piece(null, null, new Position(0, 2)));

        // when
        boolean actual = chariotRule.isAbleToThrough(legalPath, piecesOnBoard, null);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @DisplayName("차는 이동 경로에 아무런 기물도 없으면 이동할 수 있다.")
    @Test
    void chariotRouteNoCollisionTest() {
        // given
        ChariotRule chariotRule = new ChariotRule();
        Path legalPath = new Path(List.of(
                new Position(0, 1),
                new Position(0, 2),
                new Position(0, 3)
        ));
        List<Piece> piecesOnBoard = List.of();

        // when
        boolean actual = chariotRule.isAbleToThrough(legalPath, piecesOnBoard, null);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @DisplayName("차는 도착 지점에 아군이 있으면 이동하지 못한다.")
    @Test
    void chariotDestinationOurTeamTest() {
        // given
        ChariotRule chariotRule = new ChariotRule();
        Path legalPath = new Path(List.of(
                new Position(0, 1),
                new Position(0, 2),
                new Position(0, 3)
        ));
        List<Piece> piecesOnBoard = List.of(new Piece(Team.BLUE, null, new Position(0, 3)));

        // when
        boolean actual = chariotRule.isAbleToThrough(legalPath, piecesOnBoard, Team.BLUE);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @DisplayName("차는 도착 지점에 적군이 있으면 이동할 수 있다.")
    @Test
    void chariotDestinationEnemyTest() {
        // given
        ChariotRule chariotRule = new ChariotRule();
        Path legalPath = new Path(List.of(
                new Position(0, 1),
                new Position(0, 2),
                new Position(0, 3)
        ));
        List<Piece> piecesOnBoard = List.of(new Piece(Team.RED, null, new Position(0, 3)));

        // when
        boolean actual = chariotRule.isAbleToThrough(legalPath, piecesOnBoard, Team.BLUE);

        // then
        Assertions.assertThat(actual).isTrue();
    }
}
