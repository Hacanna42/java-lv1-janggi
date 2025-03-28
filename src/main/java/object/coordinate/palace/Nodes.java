package object.coordinate.palace;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import object.coordinate.Position;
import object.piece.Team;

public class Nodes {
    private static final Map<Position, List<Position>> redConnectedNodes;
    private static final Map<Position, List<Position>> blueConnectedNodes;
    private final List<Position> nodes;

    private Nodes(List<Position> nodes) {
        this.nodes = nodes;
    }

    public static Nodes generateConnectedNodesFrom(Position node, Team team) {
        Map<Position, List<Position>> connectedNodes = Objects.equals(team, Team.BLUE) ? blueConnectedNodes : redConnectedNodes;

        if (!connectedNodes.containsKey(node)) {
            throw new IllegalArgumentException("해당 Position 의 연결 노드 정보가 없습니다. 예외가 발생했습니다.");
        }

        return new Nodes(connectedNodes.get(node));
    }

    public boolean contains(Position position) {
        return nodes.contains(position);
    }

    // 궁성 영역의 특정 위치에서, 갈 수 있는 정점들을 하드코딩함.
    static {
        blueConnectedNodes = Map.ofEntries(
                /*   ■ □ □
                     □ □ □
                     □ □ □   */
                Map.entry(new Position(7, 3),
                        List.of(
                                new Position(7, 4),
                                new Position(8, 3),
                                new Position(8, 4)
                        )
                ),

                /*   □ ■ □
                     □ □ □
                     □ □ □   */
                Map.entry(new Position(7, 4),
                        List.of(
                                new Position(7, 3),
                                new Position(7, 5),
                                new Position(8, 4)
                        )
                ),

                /*   □ □ ■
                     □ □ □
                     □ □ □   */
                Map.entry(new Position(7, 5),
                        List.of(
                                new Position(7, 4),
                                new Position(8, 5),
                                new Position(8, 4)
                        )
                ),

                /*   □ □ □
                     ■ □ □
                     □ □ □   */
                Map.entry(new Position(8, 3),
                        List.of(
                                new Position(7, 3),
                                new Position(8, 4),
                                new Position(9, 3)
                        )
                ),

                /*   □ □ □
                     □ ■ □
                     □ □ □   */
                Map.entry(new Position(8, 4),
                        List.of(
                                new Position(7, 3),
                                new Position(7, 4),
                                new Position(7, 5),
                                new Position(8, 3),
                                new Position(8, 5),
                                new Position(9, 3),
                                new Position(9, 4),
                                new Position(9, 5)
                        )
                ),

                /*   □ □ □
                     □ □ ■
                     □ □ □   */
                Map.entry(new Position(8, 5),
                        List.of(
                                new Position(7, 5),
                                new Position(8, 4),
                                new Position(9, 5)
                        )
                ),

                /*   □ □ □
                     □ □ □
                     ■ □ □   */
                Map.entry(new Position(9, 3),
                        List.of(
                                new Position(8, 3),
                                new Position(8, 4),
                                new Position(9, 4)
                        )
                ),

                /*   □ □ □
                     □ □ □
                     □ ■ □   */
                Map.entry(new Position(9, 4),
                        List.of(
                                new Position(9, 3),
                                new Position(9, 5),
                                new Position(8, 4)
                        )
                ),

                /*   □ □ □
                     □ □ □
                     □ □ ■  */
                Map.entry(new Position(9, 5),
                        List.of(
                                new Position(9, 4),
                                new Position(8, 5),
                                new Position(8, 4)
                        )
                )
        );

        redConnectedNodes = Map.ofEntries(
                /*   ■ □ □
                     □ □ □
                     □ □ □   */
                Map.entry(new Position(0, 3),
                        List.of(
                                new Position(0, 4),
                                new Position(1, 3),
                                new Position(1, 4)
                        )
                ),

                /*   □ ■ □
                     □ □ □
                     □ □ □   */
                Map.entry(new Position(0, 4),
                        List.of(
                                new Position(0, 3),
                                new Position(0, 5),
                                new Position(1, 4)
                        )
                ),

                /*   □ □ ■
                     □ □ □
                     □ □ □   */
                Map.entry(new Position(0, 5),
                        List.of(
                                new Position(0, 4),
                                new Position(1, 5),
                                new Position(1, 4)
                        )
                ),

                /*   □ □ □
                     ■ □ □
                     □ □ □   */
                Map.entry(new Position(1, 3),
                        List.of(
                                new Position(0, 3),
                                new Position(1, 4),
                                new Position(2, 3)
                        )
                ),

                /*   □ □ □
                     □ ■ □
                     □ □ □   */
                Map.entry(new Position(1, 4),
                        List.of(
                                new Position(0, 3),
                                new Position(0, 4),
                                new Position(0, 5),
                                new Position(1, 3),
                                new Position(1, 5),
                                new Position(2, 3),
                                new Position(2, 4),
                                new Position(2, 5)
                        )
                ),

                /*   □ □ □
                     □ □ ■
                     □ □ □   */
                Map.entry(new Position(1, 5),
                        List.of(
                                new Position(0, 5),
                                new Position(1, 4),
                                new Position(2, 5)
                        )
                ),

                /*   □ □ □
                     □ □ □
                     ■ □ □   */
                Map.entry(new Position(2, 3),
                        List.of(
                                new Position(1, 3),
                                new Position(1, 4),
                                new Position(2, 4)
                        )
                ),

                /*   □ □ □
                     □ □ □
                     □ ■ □   */
                Map.entry(new Position(2, 4),
                        List.of(
                                new Position(2, 3),
                                new Position(2, 5),
                                new Position(1, 4)
                        )
                ),

                /*   □ □ □
                     □ □ □
                     □ □ ■  */
                Map.entry(new Position(2, 5),
                        List.of(
                                new Position(2, 4),
                                new Position(1, 5),
                                new Position(1, 4)
                        )
                )
        );
    }
}
