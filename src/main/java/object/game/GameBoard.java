package object.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import object.coordinate.Position;
import object.moverule.CannonRule;
import object.moverule.ChariotRule;
import object.moverule.ElephantRule;
import object.moverule.GeneralRule;
import object.moverule.GuardRule;
import object.moverule.HorseRule;
import object.moverule.SoldierRule;
import object.piece.Piece;
import object.piece.PieceType;
import object.piece.Team;

public class GameBoard {

    private static Map<PieceType, Integer> scoreByType;

    private final List<Piece> pieces;
    private Team currentTurn;

    public GameBoard(List<Piece> pieces) {
        this.pieces = pieces;
        currentTurn = Team.BLUE;
    }

    public static GameBoard generateToInitGameFormat() {
        List<Piece> initialPieces = new ArrayList<>(List.of(
                // 졸·병 생성
                new Piece(Team.RED, new SoldierRule(), new Position(3, 0)),
                new Piece(Team.RED, new SoldierRule(), new Position(3, 2)),
                new Piece(Team.RED, new SoldierRule(), new Position(3, 4)),
                new Piece(Team.RED, new SoldierRule(), new Position(3, 6)),
                new Piece(Team.RED, new SoldierRule(), new Position(3, 8)),

                new Piece(Team.BLUE, new SoldierRule(), new Position(6, 0)),
                new Piece(Team.BLUE, new SoldierRule(), new Position(6, 2)),
                new Piece(Team.BLUE, new SoldierRule(), new Position(6, 4)),
                new Piece(Team.BLUE, new SoldierRule(), new Position(6, 6)),
                new Piece(Team.BLUE, new SoldierRule(), new Position(6, 8)),

                // 포 생성
                new Piece(Team.RED, new CannonRule(), new Position(2, 1)),
                new Piece(Team.RED, new CannonRule(), new Position(2, 7)),

                new Piece(Team.BLUE, new CannonRule(), new Position(7, 1)),
                new Piece(Team.BLUE, new CannonRule(), new Position(7, 7)),

                // 궁 생성
                new Piece(Team.RED, new GeneralRule(), new Position(1, 4)),
                new Piece(Team.BLUE, new GeneralRule(), new Position(8, 4)),

                // 차 생성
                new Piece(Team.RED, new ChariotRule(), new Position(0, 0)),
                new Piece(Team.RED, new ChariotRule(), new Position(0, 8)),

                new Piece(Team.BLUE, new ChariotRule(), new Position(9, 0)),
                new Piece(Team.BLUE, new ChariotRule(), new Position(9, 8)),

                // 마 생성
                new Piece(Team.RED, new HorseRule(), new Position(0, 2)),
                new Piece(Team.RED, new HorseRule(), new Position(0, 7)),

                new Piece(Team.BLUE, new HorseRule(), new Position(9, 2)),
                new Piece(Team.BLUE, new HorseRule(), new Position(9, 7)),

                // 상 생성
                new Piece(Team.RED, new ElephantRule(), new Position(0, 1)),
                new Piece(Team.RED, new ElephantRule(), new Position(0, 6)),

                new Piece(Team.BLUE, new ElephantRule(), new Position(9, 1)),
                new Piece(Team.BLUE, new ElephantRule(), new Position(9, 6)),

                // 사 생성
                new Piece(Team.RED, new GuardRule(), new Position(0, 3)),
                new Piece(Team.RED, new GuardRule(), new Position(0, 5)),

                new Piece(Team.BLUE, new GuardRule(), new Position(9, 3)),
                new Piece(Team.BLUE, new GuardRule(), new Position(9, 5))
        ));

        return new GameBoard(initialPieces);
    }

    public void move(Position from, Position to) {
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

    public boolean isContinuable() {
        return 2 == pieces.stream()
                .filter(piece -> piece.isSameType(PieceType.GENERAL))
                .count();
    }

    public Team getWinTeam() {
        if (isContinuable()) {
            throw new IllegalArgumentException("게임이 종료되지 않아서 승자를 결정할 수 없습니다.");
        }

        Piece generalPieceOfWinner = pieces.stream()
                .filter(piece -> piece.isSameType(PieceType.GENERAL))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("남아있는 궁(장)을 찾을 수 없기 때문에 승자 결정에 실패했습니다."));

        return generalPieceOfWinner.getTeam();
    }

    public double getScore(Team team) {
        // 후수의 경우 1.5점을 받고 시작함
        double initialScore = team.equals(Team.BLUE) ? 0 : 1.5;
        return initialScore + sumScoreOfPieces(team);
    }

    public Team getCurrentTurn() {
        return currentTurn;
    }

    public List<Piece> getPieces() {
        return Collections.unmodifiableList(pieces);
    }

    private double sumScoreOfPieces(Team team) {
        return pieces.stream()
                .filter(piece -> piece.isSameTeam(team))
                .mapToDouble(piece -> scoreByType.getOrDefault(piece.getPieceType(), 0))
                .sum();
    }

    private void killPieceBy(Piece killerPiece) {
        Optional<Piece> willKilledPiece = pieces.stream()
                .filter(piece -> piece.isSamePosition(killerPiece) && !piece.isSameTeam(killerPiece))
                .findFirst();

        willKilledPiece.ifPresent(pieces::remove);
    }

    private void swapTurn() {
        currentTurn = currentTurn == Team.BLUE ? Team.RED : Team.BLUE;
    }

    private Piece getPieceFrom(Position position) {
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(position))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 없습니다."));
    }

    static {
        scoreByType = Map.ofEntries(
                Map.entry(PieceType.CHARIOT, 13),
                Map.entry(PieceType.CANNON, 7),
                Map.entry(PieceType.HORSE, 5),
                Map.entry(PieceType.ELEPHANT, 3),
                Map.entry(PieceType.GUARD, 3),
                Map.entry(PieceType.SOLDIER, 2)
        );
    }
}
