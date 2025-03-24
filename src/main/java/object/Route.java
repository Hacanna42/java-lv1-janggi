package object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Route {

    private final List<Coordinate> coordinates;

    public Route(final List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public static Route makeAbsolute(Coordinate standardCoordinate, Route relativeRoute) {
        List<Coordinate> footPrints = new ArrayList<>();
        Coordinate currentCoordinate = standardCoordinate;
        for (Coordinate coordinate : relativeRoute.getCoordinate()) {
            currentCoordinate = currentCoordinate.add(coordinate);
            footPrints.add(currentCoordinate);
        }

        return new Route(footPrints);
    }

    public List<Coordinate> getCoordinate() {
        return Collections.unmodifiableList(coordinates);
    }

    public Coordinate getLast() {
        return coordinates.getLast();
    }

    public int getSize() {
        return coordinates.size();
    }
}
