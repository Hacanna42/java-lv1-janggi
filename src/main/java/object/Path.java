package object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Path {

    private final List<Coordinate> coordinates;

    public Path(final List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public static Path makeAbsolute(Coordinate standardCoordinate, Path relativePath) {
        List<Coordinate> footPrints = new ArrayList<>();
        Coordinate currentCoordinate = standardCoordinate;
        for (Coordinate coordinate : relativePath.getCoordinate()) {
            currentCoordinate = currentCoordinate.add(coordinate);
            footPrints.add(currentCoordinate);
        }

        return new Path(footPrints);
    }

    public List<Coordinate> getCoordinate() {
        return Collections.unmodifiableList(coordinates);
    }

    public Coordinate getDestination() {
        return coordinates.getLast();
    }

    public int getSize() {
        return coordinates.size();
    }
}
