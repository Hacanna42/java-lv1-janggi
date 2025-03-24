package object.moverule;

import java.util.ArrayList;
import java.util.List;
import object.Coordinate;
import object.Route;
import object.piece.Piece;
import object.piece.PieceType;
import object.piece.GameBoard;
import object.piece.Team;

public class CannonRule implements MoveRule {

    @Override
    public Route getLegalRoute(Coordinate startCoordinate, Coordinate endCoordinate, Team team) {
        Coordinate minCoordinate = Coordinate.getMinCoordinate(startCoordinate, endCoordinate);
        Coordinate maxCoordinate = Coordinate.getMaxCoordinate(startCoordinate, endCoordinate);

        List<Coordinate> coordinates = new ArrayList<>();
        if (startCoordinate.isSameColumn(endCoordinate)) {
            return calculateLegalRoute(minCoordinate, maxCoordinate, coordinates, new Coordinate(1, 0));
        }
        if (startCoordinate.isSameRow(endCoordinate)) {
            return calculateLegalRoute(minCoordinate, maxCoordinate, coordinates, new Coordinate(0, 1));
        }
        throw new IllegalArgumentException(MoveRule.INVALID_POSITION);
    }

    @Override
    public boolean isAbleToThrough(Route legalRoute, List<Piece> piecesOnBoard, Team team) {
        return false;
    }


//    @Override
//    public Coordinate move(Coordinate destination, GameBoard onRouteGameBoard, Team moveTeam) {
//        validatePiecesEmpty(onRouteGameBoard);
//        Piece firstPiece = onRouteGameBoard.getFirstPiece();
//        Piece lastPiece = onRouteGameBoard.getLastPiece();
//        var onRoutePiecesSize = onRouteGameBoard.size();
//
//        validateIsFo(firstPiece, lastPiece);
//        if (!(onRoutePiecesSize == 1 || onRoutePiecesSize == 2)) {
//            throw new IllegalArgumentException(MoveRule.INVALID_POSITION);
//        }
//        if (onRoutePiecesSize == 1 && firstPiece.isSameCoordinate(destination)) {
//            throw new IllegalArgumentException(MoveRule.INVALID_POSITION);
//        }
//        if (onRoutePiecesSize == 2 && !lastPiece.isSameCoordinate(destination)) {
//            throw new IllegalArgumentException(MoveRule.INVALID_POSITION);
//        }
//        if (lastPiece.isSameCoordinate(destination) && lastPiece.isSameTeam(moveTeam)) {
//            throw new IllegalArgumentException(MoveRule.INVALID_POSITION);
//        }
//        return destination;
//    }

    public PieceType getPieceType() {
        return PieceType.CANNON;
    }

    private static Route calculateLegalRoute(Coordinate minCoordinate, Coordinate maxCoordinate, List<Coordinate> coordinates,
                                             Coordinate direction) {
        while (!minCoordinate.equals(maxCoordinate)) {
            minCoordinate = minCoordinate.add(direction);
            coordinates.add(minCoordinate);
        }
        return new Route(coordinates);
    }

    private void validatePiecesEmpty(GameBoard gameBoard) {
        if (gameBoard.size() == 0) {
            throw new IllegalArgumentException(MoveRule.INVALID_POSITION);
        }
    }

    private void validateIsFo(Piece firstPiece, Piece lastPiece) {
        if (isFo(firstPiece) || isFo(lastPiece)) {
            throw new IllegalArgumentException(MoveRule.INVALID_POSITION);
        }
    }

    private boolean isFo(Piece piece) {
        return piece.isSameType(PieceType.CANNON);
    }
}
