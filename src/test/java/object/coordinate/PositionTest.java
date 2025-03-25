package object.coordinate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositionTest {
    @Test
    void 위치_정적_팩토리_메서드_파싱_테스트() {
        // given
        String rawText = "0, 1";

        // when & then
        Assertions.assertThatNoException().isThrownBy(() -> Position.parseFrom(rawText));
    }

    @ParameterizedTest
    @CsvSource(value = {"0, 0", "5, 5", "9, 8"})
    void 위치는_숫자를_가진다(int row, int column) {
        // given

        // when & then
        Assertions.assertThatNoException().isThrownBy(() -> new Position(row, column));
    }

    @Test
    void 위치는_더할_수_있다() {
        // given
        Position position = new Position(0, 0);
        RelativePosition addPosition = new RelativePosition(0, 1);

        // when
        Position newPosition = position.add(addPosition);

        // then
        Assertions.assertThat(newPosition).isEqualTo(new Position(0, 1));
    }

    @DisplayName("위치는 보드판 경계 밖을 표현할 수 없다.")
    @ParameterizedTest
    @CsvSource(value = {"-1, 0", "10, 0", "0, -1", "0, 9"})
    void positionValidateTest(int row, int column) {
        // given

        // when & then
        Assertions.assertThatIllegalStateException().isThrownBy(() -> new Position(row, column));
    }
}
