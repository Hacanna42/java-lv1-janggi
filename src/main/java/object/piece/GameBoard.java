package object.piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import object.Coordinate;
import object.moverule.CannonRule;
import object.moverule.ChariotRule;
import object.moverule.ElephantRule;
import object.moverule.GeneralRule;
import object.moverule.GuardRule;
import object.moverule.HorseRule;
import object.moverule.SoldierRule;

public class GameBoard {

    private final List<Piece> pieces;
    private Team currentTurn;

    public GameBoard(List<Piece> pieces) {
        this.pieces = pieces;
        currentTurn = Team.BLUE;
    }

    public static GameBoard generateToInitGameFormat() {
        List<Piece> initialPieces = new ArrayList<>(List.of(
                // 졸·병 생성
                new Piece(Team.RED, new SoldierRule(), new Coordinate(3, 0)),
                new Piece(Team.RED, new SoldierRule(), new Coordinate(3, 2)),
                new Piece(Team.RED, new SoldierRule(), new Coordinate(3, 4)),
                new Piece(Team.RED, new SoldierRule(), new Coordinate(3, 6)),
                new Piece(Team.RED, new SoldierRule(), new Coordinate(3, 8)),

                new Piece(Team.BLUE, new SoldierRule(), new Coordinate(6, 0)),
                new Piece(Team.BLUE, new SoldierRule(), new Coordinate(6, 2)),
                new Piece(Team.BLUE, new SoldierRule(), new Coordinate(6, 4)),
                new Piece(Team.BLUE, new SoldierRule(), new Coordinate(6, 6)),
                new Piece(Team.BLUE, new SoldierRule(), new Coordinate(6, 8)),

                // 포 생성
                new Piece(Team.RED, new CannonRule(), new Coordinate(2, 1)),
                new Piece(Team.RED, new CannonRule(), new Coordinate(2, 7)),

                new Piece(Team.BLUE, new CannonRule(), new Coordinate(7, 1)),
                new Piece(Team.BLUE, new CannonRule(), new Coordinate(7, 7)),

                // 궁 생성
                new Piece(Team.RED, new GeneralRule(), new Coordinate(1, 4)),
                new Piece(Team.BLUE, new GeneralRule(), new Coordinate(8, 4)),

                // 차 생성
                new Piece(Team.RED, new ChariotRule(), new Coordinate(0, 0)),
                new Piece(Team.RED, new ChariotRule(), new Coordinate(0, 8)),

                new Piece(Team.BLUE, new ChariotRule(), new Coordinate(9, 0)),
                new Piece(Team.BLUE, new ChariotRule(), new Coordinate(9, 8)),

                // 마 생성
                new Piece(Team.RED, new HorseRule(), new Coordinate(0, 2)),
                new Piece(Team.RED, new HorseRule(), new Coordinate(0, 7)),

                new Piece(Team.BLUE, new HorseRule(), new Coordinate(9, 2)),
                new Piece(Team.BLUE, new HorseRule(), new Coordinate(9, 7)),

                // 상 생성
                new Piece(Team.RED, new ElephantRule(), new Coordinate(0, 1)),
                new Piece(Team.RED, new ElephantRule(), new Coordinate(0, 6)),

                new Piece(Team.BLUE, new ElephantRule(), new Coordinate(9, 1)),
                new Piece(Team.BLUE, new ElephantRule(), new Coordinate(9, 6)),

                // 사 생성
                new Piece(Team.RED, new GuardRule(), new Coordinate(0, 3)),
                new Piece(Team.RED, new GuardRule(), new Coordinate(0, 5)),

                new Piece(Team.BLUE, new GuardRule(), new Coordinate(9, 3)),
                new Piece(Team.BLUE, new GuardRule(), new Coordinate(9, 5))
        ));

        return new GameBoard(initialPieces);
    }

    public void move(Coordinate from, Coordinate to) {
        Piece selectedPiece = getPieceFrom(from);
        if (!selectedPiece.isSameTeam(currentTurn)) {
            throw new IllegalArgumentException("상대 팀의 기물을 선택할 수 없습니다.");
        }

        Piece movedPiece = selectedPiece.move(from, to, Collections.unmodifiableList(pieces));
        pieces.remove(selectedPiece);
        pieces.add(movedPiece);
        killPieceBy(movedPiece);
        swapTurn();
    }

    public void killPieceBy(Piece killerPiece) {
        Optional<Piece> willKilledPiece = pieces.stream()
                .filter(piece -> piece.isSameCoordinate(killerPiece) && !piece.isSameTeam(killerPiece))
                .findFirst();

        willKilledPiece.ifPresent(pieces::remove);
    }

    public boolean continuable() {
        return 2 == pieces.stream()
                .filter(piece -> piece.isSameType(PieceType.GENERAL))
                .count();
    }

    private void swapTurn() {
        currentTurn = currentTurn == Team.BLUE ? Team.RED : Team.BLUE;
    }

    public Piece getPieceFrom(Coordinate coordinate) {
        return pieces.stream()
                .filter(piece -> piece.isSameCoordinate(coordinate))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 없습니다."));
    }

    public Team getWinTeam() {
        if (continuable()) {
            throw new IllegalArgumentException("게임이 종료되지 않아서 승자를 결정할 수 없습니다.");
        }

        Piece generalPieceOfWinner = pieces.stream()
                .filter(piece -> piece.isSameType(PieceType.GENERAL))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("남아있는 궁(장)을 찾을 수 없기 때문에 승자 결정에 실패했습니다."));

        return generalPieceOfWinner.getTeam();
    }

    public Team getCurrentTurn() {
        return currentTurn;
    }

    public Piece getFirstPiece() {
        return pieces.getFirst();
    }

    public List<Piece> getPieces() {
        return Collections.unmodifiableList(pieces);
    }
}
