package object.piece;

import java.util.Collections;
import java.util.List;
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

    public static GameBoard generateToInitializeFormat() {
        List<Piece> initialPieces = List.of(
                // 졸·병 생성
                new Piece(Team.BLUE, new SoldierRule(), new Coordinate(3, 0)),
                new Piece(Team.BLUE, new SoldierRule(), new Coordinate(3, 2)),
                new Piece(Team.BLUE, new SoldierRule(), new Coordinate(3, 4)),
                new Piece(Team.BLUE, new SoldierRule(), new Coordinate(3, 6)),
                new Piece(Team.BLUE, new SoldierRule(), new Coordinate(3, 8)),

                new Piece(Team.RED, new SoldierRule(), new Coordinate(6, 0)),
                new Piece(Team.RED, new SoldierRule(), new Coordinate(6, 2)),
                new Piece(Team.RED, new SoldierRule(), new Coordinate(6, 4)),
                new Piece(Team.RED, new SoldierRule(), new Coordinate(6, 6)),
                new Piece(Team.RED, new SoldierRule(), new Coordinate(6, 8)),

                // 포 생성
                new Piece(Team.BLUE, new CannonRule(), new Coordinate(2, 1)),
                new Piece(Team.BLUE, new CannonRule(), new Coordinate(2, 7)),

                new Piece(Team.RED, new CannonRule(), new Coordinate(7, 1)),
                new Piece(Team.RED, new CannonRule(), new Coordinate(7, 7)),

                // 궁 생성
                new Piece(Team.BLUE, new GeneralRule(), new Coordinate(1, 4)),
                new Piece(Team.RED, new GeneralRule(), new Coordinate(8, 4)),

                // 차 생성
                new Piece(Team.BLUE, new ChariotRule(), new Coordinate(0, 0)),
                new Piece(Team.BLUE, new ChariotRule(), new Coordinate(0, 8)),

                new Piece(Team.RED, new ChariotRule(), new Coordinate(9, 0)),
                new Piece(Team.RED, new ChariotRule(), new Coordinate(9, 8)),

                // 마 생성
                new Piece(Team.BLUE, new HorseRule(), new Coordinate(0, 2)),
                new Piece(Team.BLUE, new HorseRule(), new Coordinate(0, 7)),

                new Piece(Team.RED, new HorseRule(), new Coordinate(9, 2)),
                new Piece(Team.RED, new HorseRule(), new Coordinate(9, 7)),

                // 상 생성
                new Piece(Team.BLUE, new ElephantRule(), new Coordinate(0, 1)),
                new Piece(Team.BLUE, new ElephantRule(), new Coordinate(0, 6)),

                new Piece(Team.RED, new ElephantRule(), new Coordinate(9, 1)),
                new Piece(Team.RED, new ElephantRule(), new Coordinate(9, 6)),

                // 사 생성
                new Piece(Team.BLUE, new GuardRule(), new Coordinate(0, 3)),
                new Piece(Team.BLUE, new GuardRule(), new Coordinate(0, 5)),

                new Piece(Team.RED, new GuardRule(), new Coordinate(9, 3)),
                new Piece(Team.RED, new GuardRule(), new Coordinate(9, 5))
        );

        return new GameBoard(initialPieces);
    }

    public void killPieceFrom(Piece killerPiece) {
        for (int i = 0; i < pieces.size(); i++) {
            Piece piece = pieces.get(i);
            if (killerPiece.isSameCoordinate(piece) && !killerPiece.isSameTeam(piece)) {
                pieces.remove(i);
                return;
            }
        }
    }

    public void move(Coordinate fromCoordinate, Coordinate toCoordinate) {
        Piece selectedPiece = getPieceFrom(fromCoordinate);
        Piece movedPiece = selectedPiece.move(fromCoordinate, toCoordinate, Collections.unmodifiableList(pieces));

        // TODO: 여기서부터 구현 시작.. 인터페이스도 잘 정리하고 싶다... 객체 지향 파이팅
    }

    public Team getCurrentTurn() {
        return currentTurn;
    }

    public int size() {
        return pieces.size();
    }

    public List<Piece> getPieces() {
        return Collections.unmodifiableList(pieces);
    }

    public Piece getFirstPiece() {
        return pieces.getFirst();
    }

    public Piece getLastPiece() {
        return pieces.getLast();
    }

    public Piece getPieceFrom(Coordinate coordinate) {
        return pieces.stream()
                .filter(piece -> piece.isSameCoordinate(coordinate))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 없습니다."));
    }
}
