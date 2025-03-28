package object.coordinate.palace;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import object.coordinate.Position;
import object.piece.Team;

public class Adjacency {
    private static final List<Position> redPositionsOfPalaceArea;
    private static final List<Position> bluePositionsOfPalaceArea;

    private final Map<Position, Nodes> adjacency;

    private Adjacency(Map<Position, Nodes> adjacency) {
        this.adjacency = adjacency;
    }

    public static Adjacency generateOfPalaceArea(Team team) {
        List<Position> positionsOfPalaceArea = Objects.equals(team, Team.BLUE) ? bluePositionsOfPalaceArea : redPositionsOfPalaceArea;
        Map<Position, Nodes> adjacency = new HashMap<>();

        for (Position position : positionsOfPalaceArea) {
            adjacency.put(position, Nodes.generateConnectedNodesFrom(position, team));
        }

        return new Adjacency(adjacency);
    }

    public boolean isConnected(Position from, Position to) {
        if (!adjacency.containsKey(from)) {
            return false;
        }

        Nodes connectedNodes = adjacency.get(from);
        return connectedNodes.contains(to);
    }

    static {
        redPositionsOfPalaceArea = List.of(
                new Position(0, 3),
                new Position(0, 4),
                new Position(0, 5),
                new Position(1, 3),
                new Position(1, 4),
                new Position(1, 5),
                new Position(2, 3),
                new Position(2, 4),
                new Position(2, 5)
        );

        bluePositionsOfPalaceArea = List.of(
                new Position(7, 3),
                new Position(7, 4),
                new Position(7, 5),
                new Position(8, 3),
                new Position(8, 4),
                new Position(8, 5),
                new Position(9, 3),
                new Position(9, 4),
                new Position(9, 5)
        );
    }
}
