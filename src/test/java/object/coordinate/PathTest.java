package object.coordinate;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PathTest {
    @DisplayName("Path 초기화 테스트")
    @Test
    void pathInitializeTest() {
        // given
        List<Position> positions = List.of(
                new Position(0, 0),
                new Position(1, 1)
        );

        // when
        Path path = new Path(positions);

        // then
        Assertions.assertThatIterable(path.getPositions()).containsExactlyElementsOf(positions);
    }
}
