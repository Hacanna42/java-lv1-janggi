package object.view;

import java.util.List;
import java.util.Map;
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

    public void playTurn(GameBoard gameBoard) {
        printBoard(gameBoard);
        printTurn(gameBoard);
        Coordinate from = askCoordinateOfTargetPiece();
        Coordinate to = askCoordinateOfArrivePlace();
        gameBoard.move(from, to);
    }

    public void printWinTeam(GameBoard gameBoard) {
        Team team = gameBoard.getWinTeam();
        System.out.printf("%s이 이겼습니다. 게임을 종료합니다.", team.getName());
    }

    private Coordinate askCoordinateOfArrivePlace() {
        System.out.println("이동할 위치를 다음과 같이 입력해주세요: y좌표, x좌표");
        String rawCoordinate = scanner.nextLine();

        return Coordinate.parseFrom(rawCoordinate);
    }

    private Coordinate askCoordinateOfTargetPiece() {
        System.out.println("이동시키고 싶은 기물의 위치를 다음과 같이 입력해주세요: y좌표, x좌표");
        String rawCoordinate = scanner.nextLine();

        return Coordinate.parseFrom(rawCoordinate);
    }

    private void printBoard(GameBoard gameBoard) {
        List<Piece> pieces = gameBoard.getPieces();
        Map<Coordinate, Piece> board = pieces.stream()
                .collect(Collectors.toMap(Piece::getCoordinate, Function.identity()));

        for (int row = 0; row < 10; row++) {
            System.out.printf("%-3d", row);
            for (int column = 0; column < 9; column++) {
                Piece piece = board.get(new Coordinate(row, column));
                if (piece != null) {
                    printColorByTeam(piece);
                    System.out.printf("%-3s", piece.getPieceType().getName());
                    System.out.print(exit);
                    continue;
                }
                System.out.printf("%-3s", "。");
            }
            System.out.println();
        }

        System.out.printf("%-3s", "　");
        for (int i = 0; i < 9; ++i) {
            System.out.printf("%-3s", toFullWidth(i));
        }

        System.out.println();
        System.out.println();
    }

    private void printColorByTeam(Piece piece) {
        String color = piece.getTeam() == Team.RED ? red : blue;
        System.out.print(color);
    }

    private String toFullWidth(int number) {
        StringBuilder result = new StringBuilder();
        for (char c : String.valueOf(number).toCharArray()) {
            if (Character.isDigit(c)) {
                result.append((char) (c - '0' + '０'));
                continue;
            }
            result.append(c);

        }
        return result.toString();
    }

    private void printTurn(GameBoard gameBoard) {
        Team currentTeam = gameBoard.getCurrentTurn();
        System.out.printf("%s 차례입니다.%n", currentTeam.getName());
    }
}
