package object.game.db;

import object.game.GameBoard;

public record GameBoardRecord(String currentTurn, String status) {
    public static GameBoardRecord from(GameBoard gameBoard) {
        return new GameBoardRecord(
                gameBoard.getCurrentTurn().getName(),
                gameBoard.isContinuable() ? "IN_PROGRESS" : "FINISHED"
        );
    }
}
