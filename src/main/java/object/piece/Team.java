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

    public String getName() {
        return name;
    }
}
