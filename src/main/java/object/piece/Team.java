package object.piece;

import java.util.Arrays;

public enum Team {
    RED("홍"),
    BLUE("청"),
    ;

    private static final String INVALID_TEAM = "팀은 홍 청이어야합니다.";

    Team(String name) {
        this.name = name;
    }

    private final String name;

    public static Team from(String s) {
        return Arrays.stream(Team.values())
                .filter(team -> s.equals(team.name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_TEAM));
    }

    public String getName() {
        return name;
    }
}
