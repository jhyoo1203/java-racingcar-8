package racingcar.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import racingcar.application.dto.CarsSaveRequest;
import racingcar.application.dto.TryCountSaveRequest;
import racingcar.domain.Car;
import racingcar.domain.repository.CarRepository;
import racingcar.domain.repository.RacingRepository;
import racingcar.domain.repository.util.TestRepositoryUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("RacingService 클래스 테스트")
class RacingServiceTest {

    private static final List<String> DEFAULT_CAR_NAMES = List.of("pobi", "woni");
    private static final List<String> THREE_CAR_NAMES = List.of("pobi", "woni", "jun");

    private RacingService racingService;
    private CarService carService;
    private RacingRepository racingRepository;

    @BeforeEach
    void setUp() {
        CarRepository carRepository = new CarRepository();
        carService = new CarService(carRepository);
        racingRepository = new RacingRepository();
        racingService = new RacingService(racingRepository, carService);
        TestRepositoryUtil.clearAllRepositories();
    }

    @Nested
    @DisplayName("saveTryCount 메서드는")
    class Describe_saveTryCount {

        @ParameterizedTest
        @ValueSource(ints = {1, 5, 10, 50, 100})
        @DisplayName("시도 횟수를 저장한다")
        void it_saves_try_count(int tryCount) {
            // given
            TryCountSaveRequest request = TryCountSaveRequest.from(tryCount);

            // when
            racingService.saveTryCount(request);

            // then
            assertThat(racingRepository.findTryCount()).isEqualTo(tryCount);
        }
    }

    @Nested
    @DisplayName("race 메서드는")
    class Describe_race {

        @Test
        @DisplayName("경주를 진행하고 각 라운드의 결과를 문자열로 반환한다")
        void it_runs_race_and_returns_result() {
            // given
            givenRaceIsReady(3);

            // when
            String result = racingService.race();

            // then
            assertThat(result)
                    .isNotEmpty()
                    .contains("pobi", "woni");
        }

        @ParameterizedTest
        @ValueSource(ints = {1, 5, 10})
        @DisplayName("시도 횟수만큼 차량 상태가 출력된다")
        void it_outputs_status_for_each_try(int tryCount) {
            // given
            givenRaceIsReady(tryCount);

            // when
            String result = racingService.race();

            // then
            assertThat(result.split("\n")).hasSizeGreaterThanOrEqualTo(tryCount);
        }

        @Test
        @DisplayName("경주 중 차량 정보가 업데이트된다")
        void it_updates_cars_during_race() {
            // given
            givenRaceIsReady(10);

            // when
            racingService.race();

            // then
            List<Car> cars = carService.getCars();
            int totalPosition = cars.stream()
                    .mapToInt(Car::getPosition)
                    .sum();
            assertThat(totalPosition).isPositive();
        }
    }

    @Nested
    @DisplayName("getWinners 메서드는")
    class Describe_getWinners {

        @Test
        @DisplayName("가장 멀리 간 차량의 이름을 반환한다")
        void it_returns_winner_names() {
            // given
            givenCarsAreRegistered(DEFAULT_CAR_NAMES);
            List<Car> cars = carService.getCars();

            moveCarForward(cars.get(0), 3);
            moveCarForward(cars.get(1), 1);
            carService.updateCars(cars);

            // when
            List<String> winners = racingService.getWinners();

            // then
            assertThat(winners)
                    .hasSize(1)
                    .containsExactly("pobi");
        }

        @Test
        @DisplayName("동점자가 있으면 모두 반환한다")
        void it_returns_all_winners_when_tied() {
            // given
            givenCarsAreRegistered(THREE_CAR_NAMES);
            List<Car> cars = carService.getCars();

            moveCarForward(cars.get(0), 2);
            moveCarForward(cars.get(1), 2);
            moveCarForward(cars.get(2), 1);
            carService.updateCars(cars);

            // when
            List<String> winners = racingService.getWinners();

            // then
            assertThat(winners)
                    .hasSize(2)
                    .containsExactly("pobi", "woni");
        }

        @Test
        @DisplayName("모든 차량이 이동하지 않았을 때 모두를 우승자로 반환한다")
        void it_returns_all_when_no_movement() {
            // given
            givenCarsAreRegistered(DEFAULT_CAR_NAMES);

            // when
            List<String> winners = racingService.getWinners();

            // then
            assertThat(winners)
                    .hasSize(2)
                    .containsExactly("pobi", "woni");
        }

        @Test
        @DisplayName("차량이 없으면 예외를 발생시킨다")
        void it_throws_exception_when_no_cars() {
            // when & then
            assertThatThrownBy(() -> racingService.getWinners())
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("자동차 목록이 비어 있습니다");
        }
    }

    // test fixtures
    private void givenCarsAreRegistered(List<String> carNames) {
        CarsSaveRequest request = CarsSaveRequest.from(carNames);
        carService.saveCars(request);
    }

    private void givenTryCountIsSet(int tryCount) {
        TryCountSaveRequest request = TryCountSaveRequest.from(tryCount);
        racingService.saveTryCount(request);
    }

    private void givenRaceIsReady(int tryCount) {
        givenCarsAreRegistered(RacingServiceTest.DEFAULT_CAR_NAMES);
        givenTryCountIsSet(tryCount);
    }

    private void moveCarForward(Car car, int times) {
        for (int i = 0; i < times; i++) {
            car.moveForward();
        }
    }
}
