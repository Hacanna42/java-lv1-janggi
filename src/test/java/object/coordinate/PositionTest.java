package object.coordinate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositionTest {
    @DisplayName("Position은 문자열을 자기 자신으로 파싱할 수 있다.")
    @Test
    void PositionParsingTest() {
        // given
        String rawText = "0, 1";

        // when & then
        Assertions.assertThatNoException().isThrownBy(() -> Position.parseFrom(rawText));
    }

    @DisplayName("위치는 열과 행을 가지고 있다.")
    @ParameterizedTest
    @CsvSource(value = {"0, 0", "5, 5", "9, 8"})
    void fieldOfPositionTest(int row, int column) {
        // given

        // when & then
        Assertions.assertThatNoException().isThrownBy(() -> new Position(row, column));
    }

    @DisplayName("위치는 상대적 위치(RelativePosition)과 더할 수 있다.")
    @Test
    void positionApplyTest() {
        // given
        Position position = new Position(0, 0);
        RelativePosition addPosition = new RelativePosition(0, 1);

        // when
        Position newPosition = position.apply(addPosition);

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
