package racingcar.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import racingcar.application.dto.CarsSaveRequest;
import racingcar.domain.Car;
import racingcar.domain.repository.CarRepository;
import racingcar.domain.repository.util.TestRepositoryUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("CarService 클래스 테스트")
class CarServiceTest {

    private CarService carService;

    @BeforeEach
    void setUp() {
        CarRepository carRepository = new CarRepository();
        carService = new CarService(carRepository);
        TestRepositoryUtil.clearCarRepository();
    }

    @Nested
    @DisplayName("saveCars 메서드는")
    class Describe_saveCars {

        @Test
        @DisplayName("차량 이름 리스트를 받아서 Car 객체로 변환하여 저장한다")
        void it_saves_cars_from_request() {
            // given
            CarsSaveRequest request = CarsSaveRequest.from(List.of("pobi", "woni", "jun"));

            // when
            carService.saveCars(request);

            // then
            List<Car> cars = carService.getCars();
            assertThat(cars).hasSize(3);
            assertThat(cars).extracting(Car::getName)
                    .containsExactly("pobi", "woni", "jun");
        }

        @Test
        @DisplayName("저장된 차량들의 초기 위치는 0이다")
        void it_saves_cars_with_initial_position_zero() {
            // given
            CarsSaveRequest request = CarsSaveRequest.from(List.of("pobi", "woni"));

            // when
            carService.saveCars(request);

            // then
            List<Car> cars = carService.getCars();
            assertThat(cars).extracting(Car::getPosition)
                    .containsOnly(0);
        }
    }

    @Nested
    @DisplayName("getCars 메서드는")
    class Describe_getCars {

        @Test
        @DisplayName("저장된 모든 차량을 반환한다")
        void it_returns_all_cars() {
            // given
            CarsSaveRequest request = CarsSaveRequest.from(List.of("pobi", "woni"));
            carService.saveCars(request);

            // when
            List<Car> cars = carService.getCars();

            // then
            assertThat(cars).hasSize(2);
            assertThat(cars).extracting(Car::getName)
                    .containsExactly("pobi", "woni");
        }

        @Test
        @DisplayName("저장된 차량이 없으면 빈 리스트를 반환한다")
        void it_returns_empty_list_when_no_cars() {
            // given & when
            List<Car> cars = carService.getCars();

            // then
            assertThat(cars).isEmpty();
        }
    }

    @Nested
    @DisplayName("updateCars 메서드는")
    class Describe_updateCars {

        @Test
        @DisplayName("차량 정보를 업데이트한다")
        void it_updates_cars() {
            // given
            CarsSaveRequest request = CarsSaveRequest.from(List.of("pobi", "woni"));
            carService.saveCars(request);

            List<Car> cars = carService.getCars();
            cars.get(0).moveForward();

            // when
            carService.updateCars(cars);

            // then
            List<Car> updatedCars = carService.getCars();
            assertThat(updatedCars.get(0).getPosition()).isEqualTo(1);
        }

        @Test
        @DisplayName("빈 리스트를 업데이트하려고 하면 예외를 발생시킨다")
        void it_throws_exception_when_list_is_empty() {
            // given
            List<Car> cars = List.of();

            // when & then
            assertThatThrownBy(() -> carService.updateCars(cars))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("업데이트할 차량 목록이 비어 있습니다");
        }

        @Test
        @DisplayName("null 리스트를 업데이트하려고 하면 예외를 발생시킨다")
        void it_throws_exception_when_list_is_null() {
            // given
            List<Car> cars = null;

            // when & then
            assertThatThrownBy(() -> carService.updateCars(cars))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("업데이트할 차량 목록이 비어 있습니다");
        }
    }

    @Nested
    @DisplayName("carsStatusToString 메서드는")
    class Describe_carsStatusToString {

        @Test
        @DisplayName("차량 이름과 위치를 문자열로 변환한다")
        void it_converts_cars_to_string() {
            // given
            Car car1 = Car.from("pobi");
            car1.moveForward();
            car1.moveForward();

            Car car2 = Car.from("woni");
            car2.moveForward();

            List<Car> cars = List.of(car1, car2);

            // when
            String result = carService.carsStatusToString(cars);

            // then
            assertThat(result).contains("pobi : --");
            assertThat(result).contains("woni : -");
        }

        @Test
        @DisplayName("위치가 0인 차량은 막대가 없다")
        void it_shows_no_bar_for_zero_position() {
            // given
            Car car = Car.from("pobi");
            List<Car> cars = List.of(car);

            // when
            String result = carService.carsStatusToString(cars);

            // then
            assertThat(result).contains("pobi : ");
            assertThat(result).doesNotContain("pobi : -");
        }

        @Test
        @DisplayName("각 차량 상태는 줄바꿈으로 구분된다")
        void it_separates_cars_with_newline() {
            // given
            Car car1 = Car.from("pobi");
            Car car2 = Car.from("woni");
            List<Car> cars = List.of(car1, car2);

            // when
            String result = carService.carsStatusToString(cars);

            // then
            assertThat(result).contains("\n");
            String[] lines = result.split("\n");
            assertThat(lines.length).isGreaterThanOrEqualTo(2);
        }

        @Test
        @DisplayName("빈 리스트는 빈 줄만 포함한다")
        void it_returns_newline_for_empty_list() {
            // given
            List<Car> cars = List.of();

            // when
            String result = carService.carsStatusToString(cars);

            // then
            assertThat(result).isEqualTo("\n");
        }
    }
}

