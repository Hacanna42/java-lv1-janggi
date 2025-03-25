import object.piece.GameBoard;
import object.view.GameView;

public class KoreanChessApplication {
    public static void main(String[] args) {
        GameView gameView = new GameView();
        GameBoard gameBoard = GameBoard.generateToInitGameFormat();

        do {
            try {
                gameView.playTurn(gameBoard);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        } while (gameBoard.continuable());

        gameView.printWinTeam(gameBoard);
    }
}
