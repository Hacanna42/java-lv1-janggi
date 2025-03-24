import object.piece.GameBoard;
import object.view.GameView;

public class KoreanChessApplication {
    public static void main(String[] args) {
        GameView gameView = new GameView();
        GameBoard gameBoard = GameBoard.generateToInitializeFormat();

        while (true) {
            gameView.playTurn(gameBoard);
        }
    }
}
