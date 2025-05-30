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

class CannonRuleTest {
    @DisplayName("포는 목적지로 이동 가능한 올바른 경로를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"5, 5, 5, 8", "5, 5, 5, 2", "5, 5, 8, 5", "5, 5, 2, 5"})
    void cannonRouteTest(int fromRow, int fromCol, int toRow, int toCol) {
        // given
        CannonRule cannonRule = new CannonRule();
        Position from = new Position(fromRow, fromCol);
        Position to = new Position(toRow, toCol);

        // when
        Path path = cannonRule.getLegalRoute(from, to, Team.BLUE);

        // then
        assertAll(
                () -> Assertions.assertThat(path.getSize()).isEqualTo(3),
                () -> Assertions.assertThat(path.getLast()).isEqualTo(to)
        );
    }

    @DisplayName("포는 목적지로 이동 가능한 올바른 경우가 없는 경우 예외를 발생시킨다.")
    @Test
    void cannonRouteExceptionTest() {
        // given
        CannonRule cannonRule = new CannonRule();

        // when & then
        Assertions.assertThatIllegalArgumentException().isThrownBy(() ->cannonRule.getLegalRoute(new Position(0, 0), new Position(1, 1), Team.BLUE));
    }

    @DisplayName("포는 이동 경로에 무조건 하나의 기물이 있어야 이동 가능하다 (적군, 아군 상관 없음)")
    @Test
    void cannonJumpTest() {
        // given
        CannonRule cannonRule = new CannonRule();
        Path legalPath = new Path(List.of(
                new Position(0, 1),
                new Position(0, 2),
                new Position(0, 3)
        ));
        List<Piece> piecesOnBoard = List.of(new Piece(Team.RED, new SoldierRule(), new Position(0, 2)));

        // when
        boolean actual = cannonRule.isAbleToThrough(legalPath, piecesOnBoard, Team.BLUE);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @DisplayName("포는 목적지를 포함하지 않은 이동경로에 아무 말도 없다면 이동 불가능하다.")
    @Test
    void cannonCannotJumpTest() {
        // given
        CannonRule cannonRule = new CannonRule();
        Path legalPath = new Path(List.of(
                new Position(0, 1),
                new Position(0, 2),
                new Position(0, 3)
        ));
        List<Piece> piecesOnBoard = List.of();

        // when
        boolean actual = cannonRule.isAbleToThrough(legalPath, piecesOnBoard, null);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @DisplayName("포는 첫 충돌로 포를 만나면 이동 불가능하다.")
    @Test
    void cannonFirstCrashIsPoTest() {
        // given
        CannonRule cannonRule = new CannonRule();
        Path legalPath = new Path(List.of(
                new Position(0, 1),
                new Position(0, 2),
                new Position(0, 3)
        ));
        List<Piece> piecesOnBoard = List.of(
                new Piece(Team.BLUE, new CannonRule(), new Position(0, 2))
        );

        // when
        boolean actual = cannonRule.isAbleToThrough(legalPath, piecesOnBoard, Team.BLUE);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @DisplayName("포는 충돌 1회 조건을 충족했음에도 목적지가 아군이라면 이동 불가능하다.")
    @Test
    void cannonDestinationOurTeamTest() {
        // given
        CannonRule cannonRule = new CannonRule();
        Path legalPath = new Path(List.of(
                new Position(0, 1),
                new Position(0, 2),
                new Position(0, 3)
        ));
        List<Piece> piecesOnBoard = List.of(
                new Piece(Team.BLUE, new SoldierRule(), new Position(0, 2)),
                new Piece(Team.BLUE, new SoldierRule(), new Position(0, 3))
        );

        // when
        boolean actual = cannonRule.isAbleToThrough(legalPath, piecesOnBoard, Team.BLUE);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @DisplayName("포는 충돌 1회 조건을 충족하고, 목적지에 상대 기물이 있으면 이동 가능하다.")
    @Test
    void cannonDestinationAndKillTest() {
        // given
        CannonRule cannonRule = new CannonRule();
        Path legalPath = new Path(List.of(
                new Position(0, 1),
                new Position(0, 2),
                new Position(0, 3)
        ));
        List<Piece> piecesOnBoard = List.of(
                new Piece(Team.BLUE, new SoldierRule(), new Position(0, 2)),
                new Piece(Team.RED, new SoldierRule(), new Position(0, 3))
        );

        // when
        boolean actual = cannonRule.isAbleToThrough(legalPath, piecesOnBoard, Team.BLUE);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @DisplayName("포는 충돌 1회 조건을 충족했음에도 목적지가 상대팀 포라면 이동 불가능하다.")
    @Test
    void cannonDestinationEnemyCannonTest() {
        // given
        CannonRule cannonRule = new CannonRule();
        Path legalPath = new Path(List.of(
                new Position(0, 1),
                new Position(0, 2),
                new Position(0, 3)
        ));
        List<Piece> piecesOnBoard = List.of(
                new Piece(Team.BLUE, new SoldierRule(), new Position(0, 2)),
                new Piece(Team.RED, new CannonRule(), new Position(0, 3))
        );

        // when
        boolean actual = cannonRule.isAbleToThrough(legalPath, piecesOnBoard, Team.BLUE);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @DisplayName("포는 목적지가 적군이어도, 이동 경로중 충돌이 없었다면 이동 불가능하다,")
    @Test
    void cannonDestinationEnemyTeamWithoutCollisionTest() {
        // given
        CannonRule cannonRule = new CannonRule();
        Path legalPath = new Path(List.of(
                new Position(0, 1),
                new Position(0, 2),
                new Position(0, 3)
        ));
        List<Piece> piecesOnBoard = List.of(new Piece(Team.RED, new SoldierRule(), new Position(0, 3)));

        // when
        boolean actual = cannonRule.isAbleToThrough(legalPath, piecesOnBoard, Team.BLUE);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @DisplayName("포는 이동 경로중 충돌 1회(아군, 적군 상관 없음)가 있었고 목적지에 적군이 있을 때 이동 가능하다.")
    @Test
    void cannonCanMoveToEnemyTest() {
        // given
        CannonRule cannonRule = new CannonRule();
        Path legalPath = new Path(List.of(
                new Position(0, 1),
                new Position(0, 2),
                new Position(0, 3)
        ));
        List<Piece> piecesOnBoard = List.of(
                new Piece(Team.RED, new SoldierRule(), new Position(0, 2))
        );

        // when
        boolean actual = cannonRule.isAbleToThrough(legalPath, piecesOnBoard, Team.BLUE);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @DisplayName("포가 이동 경로에서 다른 포와 만나면, 무조건 이동하지 못한다.")
    @Test
    void cannonCannotMeetCannonTest() {
        // given
        CannonRule cannonRule = new CannonRule();
        Path legalPath = new Path(List.of(
                new Position(0, 1),
                new Position(0, 2),
                new Position(0, 3)
        ));
        List<Piece> piecesOnBoard = List.of(
                new Piece(Team.RED, new CannonRule(), new Position(0, 1)),
                new Piece(Team.RED, new SoldierRule(), new Position(0, 2))
        );

        // when
        boolean actual = cannonRule.isAbleToThrough(legalPath, piecesOnBoard, Team.BLUE);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @DisplayName("올바른 충돌 이후에 포를 만나도, 무조건 이동하지 못한다.")
    @Test
    void cannonCannotMeetAfterCollisionCannonTest() {
        // given
        CannonRule cannonRule = new CannonRule();
        Path legalPath = new Path(List.of(
                new Position(0, 1),
                new Position(0, 2),
                new Position(0, 3)
        ));
        List<Piece> piecesOnBoard = List.of(
                new Piece(Team.RED, new SoldierRule(), new Position(0, 1)),
                new Piece(Team.RED, new CannonRule(), new Position(0, 2))
        );

        // when
        boolean actual = cannonRule.isAbleToThrough(legalPath, piecesOnBoard, Team.BLUE);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @DisplayName("포는 목적지에 상대 포가 있어도 이동하지 못한다.")
    @Test
    void cannonCannotKillCannonTest() {
        // given
        CannonRule cannonRule = new CannonRule();
        Path legalPath = new Path(List.of(
                new Position(0, 1),
                new Position(0, 2),
                new Position(0, 3)
        ));
        List<Piece> piecesOnBoard = List.of(
                new Piece(Team.RED, new CannonRule(), new Position(0, 3))
        );

        // when
        boolean actual = cannonRule.isAbleToThrough(legalPath, piecesOnBoard, Team.BLUE);

        // then
        Assertions.assertThat(actual).isFalse();
    }
}
