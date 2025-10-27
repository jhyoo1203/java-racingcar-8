package racingcar.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Serializer 클래스 테스트")
class SerializerTest {

    @Nested
    @DisplayName("splitByComma 메서드는")
    class Describe_splitByComma {

        @Test
        @DisplayName("쉼표로 구분된 문자열을 리스트로 변환한다")
        void it_splits_by_comma() {
            // given
            String input = "pobi,woni,jun";

            // when
            List<String> result = Serializer.splitByComma(input);

            // then
            assertThat(result).containsExactly("pobi", "woni", "jun");
        }

        @Test
        @DisplayName("단일 문자열은 크기 1의 리스트로 변환한다")
        void it_returns_single_element_list() {
            // given
            String input = "pobi";

            // when
            List<String> result = Serializer.splitByComma(input);

            // then
            assertThat(result).hasSize(1).containsExactly("pobi");
        }

        @Test
        @DisplayName("빈 문자열은 빈 요소를 포함한 리스트로 변환한다")
        void it_handles_empty_string() {
            // given
            String input = "";

            // when
            List<String> result = Serializer.splitByComma(input);

            // then
            assertThat(result).hasSize(1).containsExactly("");
        }
    }

    @Nested
    @DisplayName("parseInt 메서드는")
    class Describe_parseInt {

        @ParameterizedTest
        @CsvSource({
            "123, 123",
            "-42, -42",
            "0, 0",
            "999, 999",
            "1, 1"
        })
        @DisplayName("유효한 숫자 문자열을 정수로 변환한다")
        void it_parses_valid_numbers(String input, int expected) {
            // when
            int result = Serializer.parseInt(input);

            // then
            assertThat(result).isEqualTo(expected);
        }

        @ParameterizedTest
        @CsvSource({
            "abc",
            "12.34",
            "!@#",
            "''",
            "123abc"
        })
        @DisplayName("숫자가 아닌 문자열에 대해 예외를 발생시킨다")
        void it_throws_exception_for_invalid_number(String input) {
            // when & then
            assertThatThrownBy(() -> Serializer.parseInt(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("숫자 형식이 올바르지 않습니다");
        }
    }
}
