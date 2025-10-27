package racingcar.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomNumberInRangeTest;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("MoveCondition 클래스 테스트")
class MoveConditionTest {

    @Nested
    @DisplayName("canMove 메서드는")
    class Describe_canMove {

        @ParameterizedTest
        @ValueSource(ints = {4, 5, 6, 7, 8, 9})
        @DisplayName("랜덤 값이 4 이상일 때 true를 반환한다")
        void it_returns_true_when_random_value_is_movable(int randomValue) {
            assertRandomNumberInRangeTest(
                () -> assertThat(MoveCondition.canMove()).isTrue(),
                randomValue
            );
        }

        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 3})
        @DisplayName("랜덤 값이 4 미만일 때 false를 반환한다")
        void it_returns_false_when_random_value_is_not_movable(int randomValue) {
            assertRandomNumberInRangeTest(
                () -> assertThat(MoveCondition.canMove()).isFalse(),
                randomValue
            );
        }
    }
}
