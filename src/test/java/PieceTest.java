import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import piece.Piece;
import strategy.SangMoveStrategy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PieceTest {

    @Test
    void 피스는_좌표와_움직임_전략을_가진다(){
        Position position = new Position(1, 1);
        Assertions.assertThatNoException().isThrownBy(() -> new Piece(position,new SangMoveStrategy()));
    }
}
