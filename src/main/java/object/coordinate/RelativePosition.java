package object.coordinate;

public class RelativePosition {
    private final int row;
    private final int column;

    public RelativePosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
