package object.piece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import object.Coordinate;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import object.moverule.CannonRule;
import object.moverule.ChariotRule;
import object.moverule.SoldierRule;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GameBoardTest {

    @DisplayName("Pieces 를 기본 장기 배열로 초기화 할 수 있다.")
    @Test
    void pieceInitialSettingTest() {
        // given

        // when
        GameBoard gameBoard = GameBoard.generateToInitializeFormat();

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
                () -> assertThat(pieceTypeCount.getOrDefault(PieceType.SOLIDER, 0)).isEqualTo(10),
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


    void 피스들을_관리한다() {
        // given
        var piece = new Piece(Team.BLUE, new ChariotRule(), new Coordinate(0, 1));
        var piece2 = new Piece(Team.RED, new CannonRule(), new Coordinate(0, 2));

        // when
        GameBoard gameBoard = new GameBoard(List.of(piece, piece2));
        var currentPieces = gameBoard.getPieces();
        // then
        assertThatIterable(currentPieces).containsExactlyInAnyOrderElementsOf(List.of(piece, piece2));
    }

    @Test
    void 같은_위치에_있는_적팀_기물을_잡을_수_있다() {
        // given
        var piece1 = new Piece(Team.BLUE, new SoldierRule(), new Coordinate(0, 1));
        var piece2 = new Piece(Team.RED, new SoldierRule(), new Coordinate(0, 1));
        GameBoard gameBoard = new GameBoard(new ArrayList<>(List.of(piece1, piece2)));

        // when
        gameBoard.killPieceBy(piece1);

        // then
        assertThat(gameBoard.getPieces().size()).isEqualTo(1);
        assertThat(gameBoard.getFirstPiece()).isEqualTo(piece1);
    }

    @Test
    void 같은_위치에_있는_아군은_잡지_않는다() {
        // given
        var piece1 = new Piece(Team.BLUE, new SoldierRule(), new Coordinate(0, 1));
        var piece2 = new Piece(Team.BLUE, new SoldierRule(), new Coordinate(0, 1));

        GameBoard gameBoard = new GameBoard(new ArrayList<>(List.of(piece1, piece2)));

        // when
        gameBoard.killPieceBy(piece1);

        // then
        assertThat(gameBoard.getPieces().size()).isEqualTo(2);
    }

    @Test
    void 다른_위치의_적군은_잡을_수_없다() {
        var piece1 = new Piece(Team.BLUE, new SoldierRule(), new Coordinate(0, 2));
        var piece2 = new Piece(Team.RED, new SoldierRule(), new Coordinate(0, 1));

        GameBoard gameBoard = new GameBoard(new ArrayList<>(List.of(piece1, piece2)));

        // when
        gameBoard.killPieceBy(piece1);

        // then
        assertThat(gameBoard.getPieces().size()).isEqualTo(2);
    }

    @DisplayName("특정 좌표의 Piece를 가져올 수 있다.")
    @Test
    void getPieceFromTest() {
        // given
        Coordinate coordinate = new Coordinate(0, 0);
        Piece fakePiece = new Piece(Team.BLUE, new SoldierRule(), coordinate);
        GameBoard gameBoard = new GameBoard(List.of(fakePiece));

        // when
        Piece piece = gameBoard.getPieceFrom(coordinate);

        // then
        Assertions.assertThat(piece).isEqualTo(fakePiece);
    }

    @DisplayName("GameBoard는 특정 위치의 기물을, 원하는 위치로 이동시킬 수 있다.")
    @Test
    void gameBoardPieceMoveTest() {
        // given
        Piece originalPiece = new Piece(Team.BLUE, new SoldierRule(), new Coordinate(0, 0));
        GameBoard gameBoard = new GameBoard(new ArrayList<>(List.of(originalPiece)));

        // when
        gameBoard.move(new Coordinate(0, 0), new Coordinate(0, 1));

        // then
        Piece movedPiece = gameBoard.getPieceFrom(new Coordinate(0, 1));
        Assertions.assertThat(movedPiece.getPieceType()).isEqualTo(PieceType.SOLIDER);
    }
}
