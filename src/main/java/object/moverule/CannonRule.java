package object.moverule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import object.Coordinate;
import object.Path;
import object.piece.Piece;
import object.piece.PieceType;
import object.piece.Team;

public class CannonRule implements MoveRule {

    @Override
    public Path getLegalRoute(Coordinate startCoordinate, Coordinate endCoordinate, Team team) {
        Coordinate maxCoordinate = Coordinate.getMaxCoordinate(startCoordinate, endCoordinate);

        if (startCoordinate.isSameColumn(endCoordinate)) {
            if (maxCoordinate.equals(startCoordinate)) {
                return generateStraightRoute(startCoordinate, endCoordinate, new Coordinate(-1, 0));
            }
            return generateStraightRoute(startCoordinate, endCoordinate, new Coordinate(1, 0));
        }

        if (startCoordinate.isSameRow(endCoordinate)) {
            if (maxCoordinate.equals(startCoordinate)) {
                return generateStraightRoute(startCoordinate, endCoordinate, new Coordinate(0, -1));
            }
            return generateStraightRoute(startCoordinate, endCoordinate, new Coordinate(0, 1));
        }

        throw new IllegalArgumentException(MoveRule.INVALID_POSITION);
    }

    @Override
    public boolean isAbleToThrough(Path legalPath, List<Piece> piecesOnBoard, Team team) {
        // 규칙: 포는 목적지를 포함하지 않은 이동 경로에 무조건 포가 아닌 기물과 1회 충돌해야 함.
        Optional<Piece> candidateCollisionPiece = findFirstPieceOnRoute(legalPath, piecesOnBoard);

        // 충돌할 Piece가 없으므로, 통과 불가능함.
        if (candidateCollisionPiece.isEmpty()) {
            return false;
        }

        // 처음 충돌한 Piece가 목적지에 있으므로, 충돌로 간주하지 않음.
        Coordinate destination = legalPath.getDestination();
        if (candidateCollisionPiece.get().isSameCoordinate(destination)) {
            return false;
        }

        // 충돌한 Piece가 포라면, 통과 불가능함.
        if (candidateCollisionPiece.get().isSameType(getPieceType())) {
            return false;
        }

        // 목적지에서 1회 충돌했다면 통과 불가능함.
        if (piecesOnBoard.size() == 1 && candidateCollisionPiece.get().isSameCoordinate(legalPath.getDestination())) {
            return false;
        }

        // 2회 충돌했지만, 마지막 충돌이 목적지가 아닌 경우 통과 불가능함.
        Piece lastCollisionPiece = findLastPieceOnRoute(legalPath, piecesOnBoard);
        if (piecesOnBoard.size() == 2 && !lastCollisionPiece.isSameCoordinate(legalPath.getDestination())) {
            return false;
        }

        if (lastCollisionPiece.isSameTeam(team)) {
            return false;
        }

        return true;
    }

    private Piece findLastPieceOnRoute(Path path, List<Piece> piecesOnBoard) {
        List<Coordinate> reversedCoordinates = path.getCoordinate().reversed();
        for (Coordinate coordinate : reversedCoordinates) {
            Optional<Piece> foundPiece = piecesOnBoard.stream()
                    .filter(piece -> piece.isSameCoordinate(coordinate))
                    .findFirst();

            if (foundPiece.isPresent()) {
                return foundPiece.get();
            }
        }

        throw new IllegalArgumentException("이동 경로와 충돌하는 마지막 기물을 찾을 수 없습니다. 오류가 발생했습니다.");
    }

    public PieceType getPieceType() {
        return PieceType.CANNON;
    }

    private Path generateStraightRoute(Coordinate minCoordinate, Coordinate maxCoordinate, Coordinate direction) {
        List<Coordinate> footPrints = new ArrayList<>();

        while (!minCoordinate.equals(maxCoordinate)) {
            minCoordinate = minCoordinate.add(direction);
            footPrints.add(minCoordinate);
        }
        return new Path(footPrints);
    }
}
