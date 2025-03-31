import object.db.MySQLConnector;
import object.game.GameBoard;
import object.game.GameBoardSyncManager;
import object.game.db.GameBoardDao;
import object.piece.db.PieceDao;
import object.view.GameView;

public class KoreanChessApp {
    public static void main(String[] args) {
        MySQLConnector mySQLConnector = new MySQLConnector();
        GameBoardDao gameBoardDao = new GameBoardDao(mySQLConnector);
        PieceDao pieceDao = new PieceDao(mySQLConnector);

        GameBoardSyncManager gameBoardSyncManager = new GameBoardSyncManager(gameBoardDao, pieceDao);
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
