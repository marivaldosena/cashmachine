package cachemachine.domain.enums;

import java.util.stream.Stream;

public enum Ballot {
    HUNDRED(100),
    FIFTY(50),
    TWENTY(20),
    TEN(10),
    FIVE(5);

    private Integer value;

    Ballot(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static Stream<Ballot> stream() {
        return Stream.of(Ballot.values());
    }
}
