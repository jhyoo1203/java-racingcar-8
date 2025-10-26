package racingcar.util;

import camp.nextstep.edu.missionutils.Randoms;

public final class MoveCondition {

    private static final int MIN_RANDOM_RANGE = 0;
    private static final int MAX_RANDOM_RANGE = 9;
    private static final int MOVABLE_THRESHOLD = 4;

    private MoveCondition() { }

    public static boolean canMove() {
        int randomNumber = Randoms.pickNumberInRange(MIN_RANDOM_RANGE, MAX_RANDOM_RANGE);
        return randomNumber >= MOVABLE_THRESHOLD;
    }
}

