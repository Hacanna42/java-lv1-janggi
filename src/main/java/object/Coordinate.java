package object;

import java.util.List;
import java.util.Objects;

public class Coordinate {

    private final int row;
    private final int column;

    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public static Coordinate parseFrom(String rawTextCoordinate) {
        String[] parsedText = rawTextCoordinate.split("\\s*,\\s*");
        if (parsedText.length != 2) {
            throw new IllegalArgumentException("올바른 좌표 입력이 아닙니다. (y, x) 형태로 입력해주세요.");
        }
        int row = Integer.parseInt(parsedText[0]);
        int column = Integer.parseInt(parsedText[1]);

        return new Coordinate(row, column);
    }

    public static Coordinate getMinCoordinate(Coordinate coordinate, Coordinate otherCoordinate) {
        if (coordinate.isAbsoluteBigger(otherCoordinate)) {
            return otherCoordinate;
        }
        return coordinate;
    }

    public static Coordinate getMaxCoordinate(Coordinate coordinate, Coordinate otherCoordinate) {
        if (coordinate.isAbsoluteBigger(otherCoordinate)) {
            return coordinate;
        }
        return otherCoordinate;
    }

    public Coordinate add(Coordinate coordinate) {
        int newRow = this.row + coordinate.row;
        int newColumn = this.column + coordinate.column;

        return new Coordinate(newRow, newColumn);
    }

    public Coordinate add(Path path) {
        Coordinate resultCoordinate = this;
        List<Coordinate> coordinates = path.getCoordinate();
        for (Coordinate coordinate : coordinates) {
            resultCoordinate = resultCoordinate.add(coordinate);
        }

        return resultCoordinate;
    }

    public boolean isSameRow(Coordinate coordinate) {
        return this.row == coordinate.row;
    }

    public boolean isSameColumn(Coordinate coordinate) {
        return this.column == coordinate.column;
    }

    private boolean isAbsoluteBigger(Coordinate coordinate) {
        return (this.row + this.column) - (coordinate.row + coordinate.column) > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coordinate that = (Coordinate) o;
        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
