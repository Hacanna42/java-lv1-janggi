import object.piece.GameBoard;
import object.piece.Team;
import object.view.GameView;

public class KoreanChessApplication {
    public static void main(String[] args) {
        GameView gameView = new GameView();
        GameBoard gameBoard = GameBoard.generateToInitializeFormat();

        gameView.startGame(gameBoard);


    }
}
