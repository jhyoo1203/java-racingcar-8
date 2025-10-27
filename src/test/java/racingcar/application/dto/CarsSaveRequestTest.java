package racingcar.application.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("CarsSaveRequest 클래스 테스트")
class CarsSaveRequestTest {

    @Nested
    @DisplayName("from 메서드는")
    class Describe_from {

        @Test
        @DisplayName("유효한 차량 이름 리스트로 객체를 생성한다")
        void it_creates_request_with_valid_names() {
            // given
            List<String> carNames = List.of("pobi", "woni", "jun");

            // when
            CarsSaveRequest request = CarsSaveRequest.from(carNames);

            // then
            assertThat(request.carNames()).containsExactly("pobi", "woni", "jun");
        }
    }

    @Nested
    @DisplayName("생성자는")
    class Describe_constructor {

        @Test
        @DisplayName("차량 목록이 비어있으면 예외를 발생시킨다")
        void it_throws_exception_when_empty() {
            // given
            List<String> carNames = List.of();

            // when & then
            assertThatThrownBy(() -> CarsSaveRequest.from(carNames))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("차량 목록이 비어 있습니다");
        }

        @Test
        @DisplayName("차량 수가 2보다 작으면 예외를 발생시킨다")
        void it_throws_exception_when_less_than_min() {
            // given
            List<String> carNames = List.of("pobi");

            // when & then
            assertThatThrownBy(() -> CarsSaveRequest.from(carNames))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("차량 수는 2 이상 10 이하이어야 합니다");
        }

        @Test
        @DisplayName("차량 수가 10보다 크면 예외를 발생시킨다")
        void it_throws_exception_when_more_than_max() {
            // given
            List<String> carNames = List.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k");

            // when & then
            assertThatThrownBy(() -> CarsSaveRequest.from(carNames))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("차량 수는 2 이상 10 이하이어야 합니다");
        }

        @ParameterizedTest
        @ValueSource(strings = {"toolong", "verylongname", "", "won!@", "po@bi", "test#"})
        @DisplayName("유효하지 않은 차량 이름 형식에 대해 예외를 발생시킨다")
        void it_throws_exception_for_invalid_name_format(String invalidName) {
            // given
            List<String> carNames = List.of("pobi", invalidName);

            // when & then
            assertThatThrownBy(() -> CarsSaveRequest.from(carNames))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("차 이름 형식이 올바르지 않습니다");
        }

        @Test
        @DisplayName("차량 이름이 중복되면 예외를 발생시킨다")
        void it_throws_exception_when_names_are_duplicated() {
            // given
            List<String> carNames = List.of("pobi", "woni", "pobi");

            // when & then
            assertThatThrownBy(() -> CarsSaveRequest.from(carNames))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("차 이름이 중복되었습니다");
        }

        @Test
        @DisplayName("유효한 차량 이름으로 객체를 생성한다")
        void it_creates_valid_request() {
            // given
            List<String> carNames = List.of("pobi", "woni");

            // when
            CarsSaveRequest request = CarsSaveRequest.from(carNames);

            // then
            assertThat(request.carNames()).containsExactly("pobi", "woni");
        }

        @Test
        @DisplayName("숫자와 알파벳이 섞인 이름도 허용한다")
        void it_accepts_alphanumeric_names() {
            // given
            List<String> carNames = List.of("car1", "car2", "abc12");

            // when
            CarsSaveRequest request = CarsSaveRequest.from(carNames);

            // then
            assertThat(request.carNames()).containsExactly("car1", "car2", "abc12");
        }
    }
}
