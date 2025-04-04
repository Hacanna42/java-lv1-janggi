package object.coordinate;

import object.coordinate.palace.Adjacency;
import object.piece.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AdjacencyTest {
    @DisplayName("궁성 영역에 대한 Adjacency를 생성한다.")
    @Test
    void adjacencyOfPalaceTest() {
        // given
        Team team = Team.BLUE;

        // when
        Adjacency adjacency = Adjacency.generateOfPalaceArea(team);

        // then
        Assertions.assertThat(adjacency).isInstanceOf(Adjacency.class);
    }

    @DisplayName("현재 위치에 대해, 특정 위치로 도달할 수 있는지 여부를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"9, 4", "7, 4", "9, 3"}) // TODO: 보충 필요
    void adjacencyIsConnectedTest(int row, int column) {
        // given
        Team team = Team.BLUE;
        Adjacency palaceAdjacency = Adjacency.generateOfPalaceArea(team);
        Position centerOfArea = new Position(8, 4);
        Position canGoPosition = new Position(row, column);

        // when
        boolean actual = palaceAdjacency.isConnected(centerOfArea, canGoPosition);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @DisplayName("궁성 영역에 대해, 도달할 수 없으면 false를 반환한다.")
    @Test
    void isConnected() {
        // given
        Adjacency adjacency = Adjacency.generateOfPalaceArea(Team.BLUE);

        // when
        boolean actual = adjacency.isConnected(new Position(0, 0), new Position(1, 1));

        // then
        Assertions.assertThat(actual).isFalse();
    }
}
