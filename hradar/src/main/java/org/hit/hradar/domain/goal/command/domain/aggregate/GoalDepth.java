package org.hit.hradar.domain.goal.command.domain.aggregate;

public enum GoalDepth {

    LEVEL_1(1),
    LEVEL_2(2),
    LEVEL_3(3);

    private final int value;

    GoalDepth(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    //부모기준으로 다음 depth계산
    public static GoalDepth nextDepth(GoalDepth parentDepth) {
        if (parentDepth == LEVEL_3) {
            throw new IllegalArgumentException("최대 목표 깊이를 초과할 수 없습니다.");
        }
        return values()[parentDepth.ordinal() + 1];
    }
}
