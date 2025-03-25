package object.coordinate;

import java.util.List;
import java.util.Objects;

public class Position {
    private static int BOARD_MIN_POSITION_THRESHOLD = 0;
    private static int BOARD_ROW_POSITION_THRESHOLD = 9;
    private static int BOARD_COLUMN_POSITION_THRESHOLD = 8;

    private final int row;
    private final int column;

    public Position(int row, int column) {
        validate(row, column);
        this.row = row;
        this.column = column;
    }

    private void validate(int row, int column) {
        if (!(row >= BOARD_MIN_POSITION_THRESHOLD && row <= BOARD_ROW_POSITION_THRESHOLD)) {
            throw new IllegalStateException("보드판 범위 밖의 이동입니다.");
        }
        if (!(column >= BOARD_MIN_POSITION_THRESHOLD && column <= BOARD_COLUMN_POSITION_THRESHOLD)) {
            throw new IllegalStateException("보드판 범위 밖의 이동입니다.");
        }
    }

    public static Position parseFrom(String rawTextCoordinate) {
        String[] parsedText = rawTextCoordinate.split("\\s*,\\s*");
        if (parsedText.length != 2) {
            throw new IllegalArgumentException("올바른 좌표 입력이 아닙니다. (y, x) 형태로 입력해주세요.");
        }
        int row = Integer.parseInt(parsedText[0].trim());
        int column = Integer.parseInt(parsedText[1].trim());

        return new Position(row, column);
    }

    public static Position getMaxCoordinate(Position position, Position otherPosition) {
        if (position.isAbsoluteBigger(otherPosition)) {
            return position;
        }
        return otherPosition;
    }

    private boolean isAbsoluteBigger(Position position) {
        return (this.row + this.column) - (position.row + position.column) > 0;
    }

    public Position add(RelativePosition relativePosition) {
        int newRow = this.row + relativePosition.getRow();
        int newColumn = this.column + relativePosition.getColumn();

        return new Position(newRow, newColumn);
    }

    public Position add(RelativePath relativePath) {
        Position resultPosition = this;
        List<RelativePosition> positions = relativePath.getRelativePositions();
        for (RelativePosition position : positions) {
            resultPosition = resultPosition.add(position);
        }

        return resultPosition;
    }

    public boolean isSameRow(Position position) {
        return this.row == position.row;
    }

    public boolean isSameColumn(Position position) {
        return this.column == position.column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position that = (Position) o;
        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
