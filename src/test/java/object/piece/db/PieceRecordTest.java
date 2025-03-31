package object.piece.db;

import java.util.List;
import object.coordinate.Position;
import object.game.GameBoard;
import object.moverule.SoldierRule;
import object.piece.Piece;
import object.piece.Team;
import org.assertj.core.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceRecordTest {
    @DisplayName("GameBoard를 통해 List<PieceRecord>를 반환할 수 있다.")
    @Test
    void makeRecordsFrom() {
        // given
        Piece piece = new Piece(Team.BLUE, new SoldierRule(), new Position(0, 0));
        GameBoard gameBoard = new GameBoard(List.of(piece));

        // when
        List<PieceRecord> pieceRecords = PieceRecord.makeRecordsFrom(gameBoard);

        // then
        assertAll(
                () -> Assertions.assertThat(pieceRecords.size()).isEqualTo(1),
                () -> Assertions.assertThat(pieceRecords.getFirst()).isEqualTo(new PieceRecord("졸", "청",0 ,0))
        );
    }
}
