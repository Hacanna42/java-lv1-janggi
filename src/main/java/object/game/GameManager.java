package object.game;

import java.util.List;
import object.game.db.GameBoardDao;
import object.piece.Piece;
import object.piece.Team;
import object.piece.db.PieceMapper;
import object.game.db.GameBoardRecord;
import object.piece.db.PieceDao;
import object.piece.db.PieceRecord;

public class GameManager {
    private final GameBoardDao gameBoardDao = new GameBoardDao();
    private final PieceDao pieceDao = new PieceDao();
    private long gameSessionId = -1;

    public GameBoard setGameBoard() {
        if (gameBoardDao.isAbleToConnect()) {
            return loadOnlineGame();
        }
        return loadLocalGame();
    }

    public void updateGameSync(GameBoard gameBoard) {
        if (!gameBoardDao.isAbleToConnect()) {
            return;
        }

        gameBoardDao.update(gameSessionId, GameBoardRecord.from(gameBoard));
        pieceDao.updateAll(gameSessionId, PieceRecord.makeRecordsFrom(gameBoard));
    }

    private GameBoard loadOnlineGame() {
        gameSessionId = gameBoardDao.getActiveGameSessionId();
        if (gameSessionId == -1) {
            return startNewOnlineGame();
        }
        return load(gameSessionId);
    }

    private GameBoard loadLocalGame() {
        System.out.println("DB와 연결할 수 없습니다. 게임을 로컬에서 시작합니다.");
        return GameBoard.generateToInitGameFormat();
    }

    private GameBoard startNewOnlineGame() {
        GameBoard newGameBoard = GameBoard.generateToInitGameFormat();
        gameSessionId = gameBoardDao.create(GameBoardRecord.from(newGameBoard));
        System.out.printf("새로운 게임을 %d번 슬롯에서 시작합니다.%n", gameSessionId);
        return newGameBoard;
    }

    private GameBoard load(long gameSessionId) {
        System.out.printf("진행중인 게임이 발견되었습니다. %d번 슬롯의 게임을 재개합니다.%n", gameSessionId);
        List<Piece> loadedPieces = PieceMapper.generateGameBoardFrom(gameSessionId);
        Team currentTurn = Team.from(gameBoardDao.readCurrentTurn(gameSessionId));
        return new GameBoard(loadedPieces, currentTurn);
    }
}
