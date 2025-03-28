package object.coordinate;

import object.coordinate.palace.Nodes;
import object.piece.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class NodesTest {
    @DisplayName("Nodes에 특정 Position이 포함되어 있는지 확인할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"7, 4, true", "8, 3, true", "8, 4, true", "9, 3, false", "7, 5, false"})
    void nodesContainsTest(int row, int column, boolean expected) {
        // given
        Team team = Team.BLUE;
        Nodes nodes = Nodes.generateConnectedNodesFrom(new Position(7, 3), team);

        // when
        boolean actual = nodes.contains(new Position(row, column));

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
