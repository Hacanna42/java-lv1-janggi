package object.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import object.coordinate.Position;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import object.moverule.CannonRule;
import object.moverule.ChariotRule;
import object.moverule.GeneralRule;
import object.moverule.SoldierRule;
import object.piece.Piece;
import object.piece.PieceType;
import object.piece.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GameBoardTest {

    @DisplayName("Pieces 를 기본 장기 배열로 초기화 할 수 있다.")
    @Test
    void pieceInitialSettingTest() {
        // given

        // when
        GameBoard gameBoard = GameBoard.generateToInitGameFormat();

        // then
        List<Piece> extractedPieces = gameBoard.getPieces();
        Map<PieceType, Integer> pieceTypeCount = new HashMap<>();
        Map<Team, Integer> teamCount = new HashMap<>();
        for (Piece extractedPiece : extractedPieces) {
            PieceType currentPieceType = extractedPiece.getPieceType();
            Team currentTeam = extractedPiece.getTeam();
            pieceTypeCount.merge(currentPieceType, 1, Integer::sum);
            teamCount.merge(currentTeam, 1, Integer::sum);
        }

        assertAll(
                // 피스가 올바른 수량으로 생성되었는지 테스트
                () -> assertThat(pieceTypeCount.getOrDefault(PieceType.SOLDIER, 0)).isEqualTo(10),
                () -> assertThat(pieceTypeCount.getOrDefault(PieceType.CANNON, 0)).isEqualTo(4),
                () -> assertThat(pieceTypeCount.getOrDefault(PieceType.GENERAL, 0)).isEqualTo(2),
                () -> assertThat(pieceTypeCount.getOrDefault(PieceType.CHARIOT, 0)).isEqualTo(4),
                () -> assertThat(pieceTypeCount.getOrDefault(PieceType.HORSE, 0)).isEqualTo(4),
                () -> assertThat(pieceTypeCount.getOrDefault(PieceType.ELEPHANT, 0)).isEqualTo(4),
                () -> assertThat(pieceTypeCount.getOrDefault(PieceType.GUARD, 0)).isEqualTo(4),

                // 팀이 올바르게 생성되었는지 테스트
                () -> assertThat(teamCount.getOrDefault(Team.BLUE, 0)).isEqualTo(16),
                () -> assertThat(teamCount.getOrDefault(Team.RED, 0)).isEqualTo(16));
    }

    @Test
    void 피스들을_관리한다() {
        // given
        var piece = new Piece(Team.BLUE, new ChariotRule(), new Position(0, 1));
        var piece2 = new Piece(Team.RED, new CannonRule(), new Position(0, 2));

        // when
        GameBoard gameBoard = new GameBoard(List.of(piece, piece2));
        var currentPieces = gameBoard.getPieces();
        // then
        assertThatIterable(currentPieces).containsExactlyInAnyOrderElementsOf(List.of(piece, piece2));
    }

    @Test
    void 같은_위치에_있는_적팀_기물을_잡을_수_있다() {
        // given
        Piece piece1 = new Piece(Team.BLUE, new SoldierRule(), new Position(0, 0));
        Piece piece2 = new Piece(Team.RED, new SoldierRule(), new Position(0, 1));
        GameBoard gameBoard = new GameBoard(new ArrayList<>(List.of(piece1, piece2)));

        // when
        gameBoard.move(new Position(0, 0), new Position(0, 1));

        // then
        Piece expectedPiece = new Piece(Team.BLUE, new SoldierRule(), new Position(0, 1));
        assertThat(gameBoard.getPieces().size()).isEqualTo(1);
        assertThat(gameBoard.getPieces().getFirst()).isEqualTo(expectedPiece);
    }

    @DisplayName("GameBoard는 특정 위치의 기물을, 원하는 위치로 이동시킬 수 있다.")
    @Test
    void gameBoardPieceMoveTest() {
        // given
        Piece originalPiece = new Piece(Team.BLUE, new SoldierRule(), new Position(0, 0));
        GameBoard gameBoard = new GameBoard(new ArrayList<>(List.of(originalPiece)));

        // when
        gameBoard.move(new Position(0, 0), new Position(0, 1));

        // then
        Piece expectedPiece = new Piece(Team.BLUE, new SoldierRule(), new Position(0, 1));
        Assertions.assertThat(gameBoard.getPieces().getFirst()).isEqualTo(expectedPiece);
    }

    @DisplayName("GameBoard는 궁이 2개 일때, 게임이 진행 가능하다고 판단한다.")
    @Test
    void gameBoardIsContinuableTest() {
        // given
        GameBoard gameBoard = new GameBoard(List.of(
                new Piece(Team.BLUE, new GeneralRule(), null),
                new Piece(Team.RED, new GeneralRule(), null)
        ));

        // when
        boolean expected = gameBoard.isContinuable();

        // then
        Assertions.assertThat(expected).isTrue();
    }

    @DisplayName("GameBoard는 궁이 2개가 아닐때, 게임이 진행 불가능하다고 판단한다.")
    @Test
    void gameBoardNotIsContinuableTest() {
        // given
        GameBoard gameBoard = new GameBoard(List.of(
                new Piece(Team.BLUE, new GeneralRule(), null),
                new Piece(Team.RED, new SoldierRule(), null)
        ));

        // when
        boolean expected = gameBoard.isContinuable();

        // then
        Assertions.assertThat(expected).isFalse();
    }

    @DisplayName("GameBoard는 궁이 하나만 남았을 때, 남아있는 궁의 팀을 승자 팀으로 결정한다")
    @Test
    void gameBoardWinnerTest() {
        // given
        GameBoard gameBoard = new GameBoard(List.of(
                new Piece(Team.BLUE, new GeneralRule(), null),
                new Piece(Team.RED, new SoldierRule(), null)
        ));

        // when
        Team actualWinnerTeam = gameBoard.getWinTeam();

        // then
        Team expectedWinnerTeam = Team.BLUE;
        Assertions.assertThat(actualWinnerTeam).isEqualTo(expectedWinnerTeam);
    }

    @DisplayName("GameBoard는 현재 차례가 아닌 기물을 이동시킬 수 없다.")
    @Test
    void gameBoardCurrentTurnMoveTest() {
        // given
        GameBoard gameBoard = new GameBoard(List.of(
                new Piece(Team.RED, new SoldierRule(), new Position(0, 0))
        ));

        // when & then
        Assertions.assertThat(gameBoard.getCurrentTurn()).as("이 테스트는 팀이 BLUE일 때 실행되어야 합니다.").isEqualTo(Team.BLUE);
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> gameBoard.move(new Position(0, 0), null));
    }

    @DisplayName("GameBoard는 기물 이동 이후 차례를 변경한다.")
    @Test
    void gameBoardSwapTurnTest() {
        // given
        GameBoard gameBoard = new GameBoard(new ArrayList<>(List.of(
                new Piece(Team.BLUE, new SoldierRule(), new Position(0, 0))
        )));

        // when
        Team beforeTeam = gameBoard.getCurrentTurn();
        gameBoard.move(new Position(0, 0), new Position(0, 1));

        // then
        Assertions.assertThat(beforeTeam).isNotEqualTo(gameBoard.getCurrentTurn());
    }
}
