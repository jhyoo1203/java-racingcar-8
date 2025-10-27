package racingcar.domain.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import racingcar.domain.repository.util.TestRepositoryUtil;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("RacingRepository 클래스 테스트")
class RacingRepositoryTest {

    private RacingRepository racingRepository;

    @BeforeEach
    void setUp() {
        racingRepository = new RacingRepository();
        TestRepositoryUtil.clearRacingRepository();
    }

    @Nested
    @DisplayName("saveTryCount 메서드는")
    class Describe_saveTryCount {

        @Test
        @DisplayName("시도 횟수를 저장한다")
        void it_saves_try_count() {
            // given
            int tryCount = 5;

            // when
            racingRepository.saveTryCount(tryCount);

            // then
            assertThat(racingRepository.findTryCount()).isEqualTo(5);
        }

        @Test
        @DisplayName("시도 횟수를 덮어쓴다")
        void it_overwrites_try_count() {
            // given
            racingRepository.saveTryCount(3);

            // when
            racingRepository.saveTryCount(7);

            // then
            assertThat(racingRepository.findTryCount()).isEqualTo(7);
        }

        @Test
        @DisplayName("0을 저장할 수 있다")
        void it_can_save_zero() {
            // given
            int tryCount = 0;

            // when
            racingRepository.saveTryCount(tryCount);

            // then
            assertThat(racingRepository.findTryCount()).isZero();
        }
    }

    @Nested
    @DisplayName("findTryCount 메서드는")
    class Describe_findTryCount {

        @Test
        @DisplayName("저장된 시도 횟수를 반환한다")
        void it_returns_saved_try_count() {
            // given
            racingRepository.saveTryCount(10);

            // when
            int tryCount = racingRepository.findTryCount();

            // then
            assertThat(tryCount).isEqualTo(10);
        }

        @Test
        @DisplayName("저장하지 않았을 때 기본값 0을 반환한다")
        void it_returns_default_value() {
            // given & when
            int tryCount = racingRepository.findTryCount();

            // then
            assertThat(tryCount).isZero();
        }
    }
}
