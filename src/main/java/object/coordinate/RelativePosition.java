package object.coordinate;

public class RelativePosition {
    private final int row;
    private final int column;

    public RelativePosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public static RelativePosition left() {
        return new RelativePosition(0, -1);
    }

    public static RelativePosition right() {
        return new RelativePosition(0, 1);
    }

    public static RelativePosition up() {
        return new RelativePosition(-1, 0);
    }

    public static RelativePosition down() {
        return new RelativePosition(1, 0);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
