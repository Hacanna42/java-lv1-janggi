package object.coordinate;

import java.util.Collections;
import java.util.List;

public class Path {
    private final List<Position> positions;

    public Path(final List<Position> positions) {
        this.positions = positions;
    }

    public boolean contains(Position position) {
        return positions.stream()
                .anyMatch(comparePosition -> comparePosition.equals(position));
    }

    public List<Position> getPositions() {
        return Collections.unmodifiableList(positions);
    }

    public Position getLast() {
        return positions.getLast();
    }

    public int getSize() {
        return positions.size();
    }
}
