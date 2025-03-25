package object.coordinate;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RelativePathTest {
    @DisplayName("RelativePath는 절대 좌표로 변환할 수 있다.")
    @Test
    void relativePathToAbsoluteTest() {
        // given
        RelativePath relativePath = new RelativePath(List.of(
                new RelativePosition(0, 0),
                new RelativePosition(0, 1)
        ));

        // when
        Path absolutePath = relativePath.makeAbsolutePath(new Position(5, 5));

        // then
        List<Position> actualPositions = absolutePath.getPositions();
        List<Position> expectedPositions = List.of(
                new Position(5, 5),
                new Position(5, 6)
        );
        Assertions.assertThat(actualPositions).containsExactlyElementsOf(expectedPositions);
    }
}
