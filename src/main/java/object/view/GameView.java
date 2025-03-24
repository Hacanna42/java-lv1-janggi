package object.view;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import object.Coordinate;
import object.piece.GameBoard;
import object.piece.Piece;
import object.piece.Team;

public class GameView {
    public static final String blue = "\u001B[34m";
    public static final String red = "\u001B[31m";
    public static final String exit = "\u001B[0m";

    private final Scanner scanner;

    public GameView() {
        this.scanner = new Scanner(System.in);
    }

    public void startGame(GameBoard gameBoard) {
        printBoard(gameBoard);
        printTurn(gameBoard);
        Coordinate fromCoordinate = askCoordinateOfTargetPiece();
        Coordinate toCoordinate = askCoordinateOfArrivePlace();
        gameBoard.move(fromCoordinate, toCoordinate);
    }

    private Coordinate askCoordinateOfArrivePlace() {
        System.out.println("이동할 위치를 다음과 같이 입력해주세요: (y좌표, x좌표)");
        String rawCoordinate = scanner.nextLine();

        return Coordinate.parseFrom(rawCoordinate);
    }

    private Coordinate askCoordinateOfTargetPiece() {
        System.out.println("이동시키고 싶은 기물의 위치를 다음과 같이 입력해주세요: (y좌표, x좌표)");
        String rawCoordinate = scanner.nextLine();

        return Coordinate.parseFrom(rawCoordinate);
    }

    private void printBoard(GameBoard gameBoard) {
        List<Piece> pieces = gameBoard.getPieces();
        Map<Coordinate, Piece> board = pieces.stream()
                .collect(Collectors.toMap(Piece::getCoordinate, Function.identity()));

        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 9; column++) {
                Optional<Piece> piece = Optional.ofNullable(board.get(new Coordinate(row, column)));
                if (piece.isPresent()) {
                    if (piece.get().getTeam() == Team.RED) {
                        System.out.print(blue);
                    } else {
                        System.out.print(red);
                    }
                    System.out.printf("%-3s", piece.get().getPieceType().getName());
                    System.out.print(exit);
                    continue;
                }
                System.out.printf("%-3s", "。");
            }
            System.out.println();
        }
    }

    private void printTurn(GameBoard gameBoard) {
        Team currentTeam = gameBoard.getCurrentTurn();
        System.out.printf("%s 차례입니다.%n", currentTeam.getName());
    }
}
