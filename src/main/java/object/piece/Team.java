package object.piece;

import java.util.Arrays;

public enum Team {
    RED("홍"),
    BLUE("청"),
    ;

    Team(String name) {
        this.name = name;
    }

    private final String name;

    public static Team from(String text) {
        return Arrays.stream(Team.values())
                .filter(team -> text.equals(team.name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("팀은 홍 혹은 청이어야 합니다."));
    }

    public String getName() {
        return name;
    }
}
