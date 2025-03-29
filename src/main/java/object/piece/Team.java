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

    public static Team from(String value) {
        for (Team team : Team.values()) {
            if (team.name.equals(value)) {
                return team;
            }
        }

        throw new IllegalArgumentException("올바른 팀을 매핑하는데에 실패했습니다.");
    }
    public String getName() {
        return name;
    }
}
