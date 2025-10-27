package racingcar.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Car 클래스 테스트")
class CarTest {

    @Nested
    @DisplayName("from 메서드는")
    class Describe_from {

        @Test
        @DisplayName("주어진 이름으로 Car 객체를 생성한다")
        void it_creates_car_with_name() {
            // given
            String name = "pobi";

            // when
            Car car = Car.from(name);

            // then
            assertThat(car.getName()).isEqualTo(name);
            assertThat(car.getPosition()).isZero();
        }
    }

    @Nested
    @DisplayName("moveForward 메서드는")
    class Describe_moveForward {

        @Test
        @DisplayName("position을 1 증가시킨다")
        void it_increases_position_by_one() {
            // given
            Car car = Car.from("pobi");
            int initialPosition = car.getPosition();

            // when
            car.moveForward();

            // then
            assertThat(car.getPosition()).isEqualTo(initialPosition + 1);
        }

        @ParameterizedTest
        @CsvSource({
            "1, 1",
            "2, 2",
            "3, 3",
            "5, 5",
            "10, 10"
        })
        @DisplayName("여러 번 호출하면 position이 누적된다")
        void it_accumulates_position(int moveCount, int expectedPosition) {
            // given
            Car car = Car.from("pobi");

            // when
            for (int i = 0; i < moveCount; i++) {
                car.moveForward();
            }

            // then
            assertThat(car.getPosition()).isEqualTo(expectedPosition);
        }
    }

    @Nested
    @DisplayName("getName 메서드는")
    class Describe_getName {

        @Test
        @DisplayName("생성 시 설정한 이름을 반환한다")
        void it_returns_name() {
            // given
            String name = "pobi";
            Car car = Car.from(name);

            // when
            String result = car.getName();

            // then
            assertThat(result).isEqualTo(name);
        }
    }

    @Nested
    @DisplayName("getPosition 메서드는")
    class Describe_getPosition {

        @Test
        @DisplayName("초기 position은 0이다")
        void it_returns_initial_position_zero() {
            // given
            Car car = Car.from("pobi");

            // when
            int position = car.getPosition();

            // then
            assertThat(position).isZero();
        }

        @Test
        @DisplayName("이동 후 position을 반환한다")
        void it_returns_position_after_move() {
            // given
            Car car = Car.from("pobi");
            car.moveForward();

            // when
            int position = car.getPosition();

            // then
            assertThat(position).isEqualTo(1);
        }
    }
}
