package object.moverule;

import java.util.List;
import object.coordinate.Path;
import object.coordinate.Position;
import object.piece.Piece;
import object.piece.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GeneralRuleTest {
    @DisplayName("General은 목적지로 이동 가능한 올바른 경로를 반환한다.")
    @Test
    void generalLegalPathTest() {
        // given
        GeneralRule generalRule = new GeneralRule();
        Position fromPosition = new Position(9, 3);
        Position toPosition = new Position(8, 4);

        // when
        Path path = generalRule.getLegalPath(fromPosition, toPosition, Team.BLUE);

        // then
        Assertions.assertThat(path.getSize()).isEqualTo(1);
    }

    @DisplayName("General은 목적지로 이동 불가능하면 예외를 던진다.")
    @Test
    void generalExceptionTest() {
        // given
        GeneralRule generalRule = new GeneralRule();
        Position fromPosition = new Position(9, 3);
        Position toPosition = new Position(7, 3);

        // when & then
        Assertions.assertThatIllegalArgumentException().isThrownBy(() ->
                generalRule.getLegalPath(fromPosition, toPosition, Team.BLUE)
        );
    }

    @DisplayName("General은 경로가 비어있다면 무조건 통과 가능하다.")
    @Test
    void generalEmptyPathTest() {
        // given
        GeneralRule generalRule = new GeneralRule();
        Path path = new Path(List.of(new Position(8, 4)));

        // when
        boolean actual = generalRule.isAbleToThrough(path, List.of(), Team.BLUE);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @DisplayName("General은 경로에 아군이 있다면 통과 불가능하다.")
    @Test
    void generalCannotThroughTeam() {
        // given
        GeneralRule generalRule = new GeneralRule();
        Path path = new Path(List.of(new Position(8, 4)));
        Piece fakePiece = new Piece(Team.BLUE, new SoldierRule(), new Position(8, 4));

        // when
        boolean actual = generalRule.isAbleToThrough(path, List.of(fakePiece), Team.BLUE);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @DisplayName("General은 경로에 적군이 있다면 통과 가능하다 (목적지기에 잡을 수 있다)")
    @Test
    void generalCanThroughEnemy() {
        // given
        GeneralRule generalRule = new GeneralRule();
        Path path = new Path(List.of(new Position(8, 4)));
        Piece fakePiece = new Piece(Team.RED, new SoldierRule(), new Position(8, 4));

        // when
        boolean actual = generalRule.isAbleToThrough(path, List.of(fakePiece), Team.BLUE);

        // then
        Assertions.assertThat(actual).isTrue();
    }

}
