package object.coordinate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RelativePath {
    private final List<RelativePosition> relativePath;

    public RelativePath(List<RelativePosition> relativePath) {
        this.relativePath = relativePath;
    }

    public Path makeAbsolutePath(Position standardPosition) {
        List<Position> path = new ArrayList<>();
        Position currentPosition = standardPosition;
        for (RelativePosition relativePosition : relativePath) {
            currentPosition = currentPosition.apply(relativePosition);
            path.add(currentPosition);
        }

        return new Path(path);
    }

    public List<RelativePosition> getRelativePositions() {
        return Collections.unmodifiableList(relativePath);
    }
}
