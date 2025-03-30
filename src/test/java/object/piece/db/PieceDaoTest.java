package object.piece.db;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.sql.Connection;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceDaoTest {
    /*
     * ! 주의 !
     * FakeConnectionPieceDao 를 사용하여 테스트할 것
     */
    private final FakeConnectionPieceDao dao = new FakeConnectionPieceDao();

    @BeforeEach
    void initializeTable() {
        if (dao.getClass() != FakeConnectionPieceDao.class) {
            throw new RuntimeException("테스트 코드가 접근하는 DAO 클래스가 올바르지 않습니다. 프로덕션용 DAO를 사용하고 있지는 않은지 점검하세요.");
        }

        dao.deleteAllTestTable();
    }

    @DisplayName("DB 연결 테스트")
    @Test
    void isAbleToConnect() throws Exception {
        // when & then
        try (Connection connection = dao.getConnection()) {
            assertAll(
                    () -> Assertions.assertThat(connection).isNotNull(),
                    () -> Assertions.assertThat(connection).isInstanceOf(Connection.class)
            );
        }
    }

    @DisplayName("특정 게임 세션의 모든 기물 정보를 업데이트 할 수 있다.")
    @Test
    void updateAll() {
        // given
        PieceRecord pieceRecord = new PieceRecord("졸", "홍", 0, 0);

        // when
        dao.updateAll(1, List.of(pieceRecord));

        // then
        List<PieceRecord> pieceRecords = dao.readAllTestTable();
        PieceRecord readPieceRecord = pieceRecords.getFirst();
        assertAll(
                () -> Assertions.assertThat(pieceRecords.size()).isEqualTo(1),
                () -> Assertions.assertThat(readPieceRecord).isEqualTo(pieceRecord)
        );
    }

    @DisplayName("기물 정보를 업데이트 할때에는, 해당 세션의 모든 기물이 삭제되고, 덮어 씌워진다.")
    @Test
    void updateAll2() {
        // given
        PieceRecord pastPieceRecord1 = new PieceRecord("졸", "홍", 0, 0);
        PieceRecord pastPieceRecord2 = new PieceRecord("병", "홍", 1, 1);
        dao.updateAll(1, List.of(pastPieceRecord1, pastPieceRecord2));

        // when
        PieceRecord futurePieceRecord1 = new PieceRecord("포", "홍", 2, 2);
        dao.updateAll(1, List.of(futurePieceRecord1));

        // then
        List<PieceRecord> pieceRecords = dao.readAllTestTable();
        PieceRecord readPieceRecord = pieceRecords.getFirst();
        assertAll(
                () -> Assertions.assertThat(pieceRecords.size()).isEqualTo(1),
                () -> Assertions.assertThat(readPieceRecord).isEqualTo(futurePieceRecord1)
        );
    }

    @DisplayName("특정 게임 세션의 기물 정보를 가져올 수 있다.")
    @Test
    void readAll() {
        // given
        dao.updateAll(1, List.of(new PieceRecord("졸", "홍", 0, 0)));

        // when
        List<PieceRecord> pieceRecords = dao.readAll(1);

        // then
        assertAll(
                () -> Assertions.assertThat(pieceRecords.size()).isEqualTo(1),
                () -> Assertions.assertThat(pieceRecords.getFirst().type()).isEqualTo("졸"),
                () -> Assertions.assertThat(pieceRecords.getFirst().team()).isEqualTo("홍"),
                () -> Assertions.assertThat(pieceRecords.getFirst().positionRow()).isEqualTo(0),
                () -> Assertions.assertThat(pieceRecords.getFirst().positionColumn()).isEqualTo(0)
        );
    }
}
