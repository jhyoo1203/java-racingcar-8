package racingcar.application.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("TryCountSaveRequest 클래스 테스트")
class TryCountSaveRequestTest {

    @Nested
    @DisplayName("from 메서드는")
    class Describe_from {

        @Test
        @DisplayName("유효한 시도 횟수로 객체를 생성한다")
        void it_creates_request_with_valid_count() {
            // given
            int tryCount = 5;

            // when
            TryCountSaveRequest request = TryCountSaveRequest.from(tryCount);

            // then
            assertThat(request.tryCount()).isEqualTo(5);
        }
    }

    @Nested
    @DisplayName("생성자는")
    class Describe_constructor {

        @ParameterizedTest
        @ValueSource(ints = {0, -1, -10, 101, 200})
        @DisplayName("유효하지 않은 시도 횟수에 대해 예외를 발생시킨다")
        void it_throws_exception_for_invalid_count(int tryCount) {
            // when & then
            assertThatThrownBy(() -> TryCountSaveRequest.from(tryCount))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("시도 횟수는 1 이상 100 이하이어야 합니다");
        }

        @ParameterizedTest
        @ValueSource(ints = {1, 2, 50, 99, 100})
        @DisplayName("유효한 시도 횟수로 객체를 생성한다")
        void it_creates_request_with_valid_count(int tryCount) {
            // when
            TryCountSaveRequest request = TryCountSaveRequest.from(tryCount);

            // then
            assertThat(request.tryCount()).isEqualTo(tryCount);
        }
    }
}
