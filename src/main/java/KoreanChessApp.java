import object.game.GameBoard;
import object.game.GameManager;
import object.view.GameView;

public class KoreanChessApp {
    public static void main(String[] args) {
        GameManager gameManager = new GameManager();
        GameView gameView = new GameView();
        GameBoard gameBoard = gameManager.setGameBoard();

        do {
            try {
                gameView.playTurn(gameBoard);
                gameManager.updateGameSync(gameBoard);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        } while (gameBoard.isContinuable());

        gameView.printWinTeam(gameBoard);
    }
}
