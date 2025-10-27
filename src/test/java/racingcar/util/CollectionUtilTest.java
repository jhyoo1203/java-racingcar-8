package racingcar.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CollectionUtil 클래스 테스트")
class CollectionUtilTest {

    @Nested
    @DisplayName("isEmpty 메서드는")
    class Describe_isEmpty {

        @Test
        @DisplayName("null 컬렉션에 대해 true를 반환한다")
        void it_returns_true_for_null() {
            // given
            List<String> collection = null;

            // when
            boolean result = CollectionUtil.isEmpty(collection);

            // then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("빈 컬렉션에 대해 true를 반환한다")
        void it_returns_true_for_empty_collection() {
            // given
            List<String> collection = new ArrayList<>();

            // when
            boolean result = CollectionUtil.isEmpty(collection);

            // then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("요소가 있는 컬렉션에 대해 false를 반환한다")
        void it_returns_false_for_non_empty_collection() {
            // given
            List<String> collection = List.of("pobi", "woni");

            // when
            boolean result = CollectionUtil.isEmpty(collection);

            // then
            assertThat(result).isFalse();
        }
    }
}
