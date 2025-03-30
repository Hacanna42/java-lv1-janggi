package object.piece.db;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import object.coordinate.Position;
import object.moverule.SoldierRule;
import object.piece.Piece;
import object.piece.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceMapperTest {
    @DisplayName("PieceMapper는 PieceRecord를 실제 Piece 객체로 매핑한다.")
    @Test
    void generatePiecesFrom() {
        // given
        PieceRecord pieceRecord = new PieceRecord("졸", "홍", 0, 0);

        // when
        List<Piece> pieces = PieceMapper.generatePiecesFrom(List.of(pieceRecord));

        // then
        Piece expectedPiece = new Piece(Team.RED, new SoldierRule(), new Position(0, 0));
        assertAll(
                () -> Assertions.assertThat(pieces.size()).isEqualTo(1),
                () -> Assertions.assertThat(pieces.getFirst()).isEqualTo(expectedPiece)
        );
    }
}
