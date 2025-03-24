package object;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CoordinateTest {
    @Test
    void 좌표_정적_팩토리_메서드_파싱_테스트() {
        // given
        String rawText = "0, 1";

        // when & then
        Assertions.assertThatNoException().isThrownBy(() -> Coordinate.parseFrom(rawText));
    }

    @Test
    void 좌표는_숫자를_가진다() {
        // given

        // when

        // then
        Assertions.assertThatNoException().isThrownBy(() -> new Coordinate(0, 0));
    }

    @Test
    void 좌표는_더할_수_있다() {
        // given
        Coordinate coordinate = new Coordinate(0, 0);
        Coordinate addCoordinate = new Coordinate(0, 1);

        // when
        Coordinate newCoordinate = coordinate.add(addCoordinate);

        // then
        Assertions.assertThat(newCoordinate).isEqualTo(new Coordinate(0, 1));
    }
}
