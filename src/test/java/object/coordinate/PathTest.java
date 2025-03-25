package object.coordinate;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PathTest {
    @Test
    void 루트는_경로를_가진다() {
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
