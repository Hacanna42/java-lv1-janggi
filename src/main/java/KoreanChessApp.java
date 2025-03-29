import object.game.GameBoard;
import object.game.GameBoardSyncManager;
import object.view.GameView;

public class KoreanChessApp {
    public static void main(String[] args) {
        GameBoardSyncManager gameBoardSyncManager = new GameBoardSyncManager();
        GameView gameView = new GameView();
        GameBoard gameBoard = gameBoardSyncManager.loadGameBoard();

        do {
            try {
                gameView.playTurn(gameBoard);
                gameBoardSyncManager.updateGameSync(gameBoard);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        } while (gameBoard.isContinuable());

        gameView.printWinTeam(gameBoard);
    }
}
