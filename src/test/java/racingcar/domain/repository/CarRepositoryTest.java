package racingcar.domain.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import racingcar.domain.Car;
import racingcar.domain.repository.util.TestRepositoryUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("CarRepository 클래스 테스트")
class CarRepositoryTest {

    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
        TestRepositoryUtil.clearCarRepository();
    }

    @Nested
    @DisplayName("saveAll 메서드는")
    class Describe_saveAll {

        @Test
        @DisplayName("차량 리스트를 저장한다")
        void it_saves_cars() {
            // given
            List<Car> cars = List.of(
                    Car.from("pobi"),
                    Car.from("woni")
            );

            // when
            carRepository.saveAll(cars);

            // then
            List<Car> savedCars = carRepository.findAll();
            assertThat(savedCars).hasSize(2);
            assertThat(savedCars).extracting(Car::getName)
                    .containsExactly("pobi", "woni");
        }

        @Test
        @DisplayName("빈 리스트를 저장하려고 하면 예외를 발생시킨다")
        void it_throws_exception_when_list_is_empty() {
            // given
            List<Car> cars = List.of();

            // when & then
            assertThatThrownBy(() -> carRepository.saveAll(cars))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("저장할 차량 목록이 비어 있습니다");
        }

        @Test
        @DisplayName("null 리스트를 저장하려고 하면 예외를 발생시킨다")
        void it_throws_exception_when_list_is_null() {
            // given
            List<Car> cars = null;

            // when & then
            assertThatThrownBy(() -> carRepository.saveAll(cars))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("저장할 차량 목록이 비어 있습니다");
        }

        @Test
        @DisplayName("같은 이름의 차량을 저장하면 덮어쓴다")
        void it_overwrites_car_with_same_name() {
            // given
            Car car1 = Car.from("pobi");
            car1.moveForward();
            carRepository.saveAll(List.of(car1));

            Car car2 = Car.from("pobi");

            // when
            carRepository.saveAll(List.of(car2));

            // then
            List<Car> savedCars = carRepository.findAll();
            assertThat(savedCars).hasSize(1);
            assertThat(savedCars.get(0).getPosition()).isZero();
        }
    }

    @Nested
    @DisplayName("findAll 메서드는")
    class Describe_findAll {

        @Test
        @DisplayName("저장된 모든 차량을 반환한다")
        void it_returns_all_cars() {
            // given
            List<Car> cars = List.of(
                    Car.from("pobi"),
                    Car.from("woni"),
                    Car.from("jun")
            );
            carRepository.saveAll(cars);

            // when
            List<Car> foundCars = carRepository.findAll();

            // then
            assertThat(foundCars).hasSize(3);
            assertThat(foundCars).extracting(Car::getName)
                    .containsExactly("pobi", "woni", "jun");
        }

        @Test
        @DisplayName("저장된 차량이 없으면 빈 리스트를 반환한다")
        void it_returns_empty_list_when_no_cars() {
            // given & when
            List<Car> foundCars = carRepository.findAll();

            // then
            assertThat(foundCars).isEmpty();
        }
    }

    @Nested
    @DisplayName("updateAll 메서드는")
    class Describe_updateAll {

        @Test
        @DisplayName("차량 정보를 업데이트한다")
        void it_updates_cars() {
            // given
            Car car1 = Car.from("pobi");
            Car car2 = Car.from("woni");
            carRepository.saveAll(List.of(car1, car2));

            car1.moveForward();
            car1.moveForward();

            // when
            carRepository.updateAll(List.of(car1));

            // then
            List<Car> updatedCars = carRepository.findAll();
            assertThat(updatedCars.get(0).getPosition()).isEqualTo(2);
        }

        @Test
        @DisplayName("빈 리스트를 업데이트하려고 하면 예외를 발생시킨다")
        void it_throws_exception_when_list_is_empty() {
            // given
            List<Car> cars = List.of();

            // when & then
            assertThatThrownBy(() -> carRepository.updateAll(cars))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("업데이트할 차량 목록이 비어 있습니다");
        }

        @Test
        @DisplayName("null 리스트를 업데이트하려고 하면 예외를 발생시킨다")
        void it_throws_exception_when_list_is_null() {
            // given
            List<Car> cars = null;

            // when & then
            assertThatThrownBy(() -> carRepository.updateAll(cars))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("업데이트할 차량 목록이 비어 있습니다");
        }

        @Test
        @DisplayName("저장소에 없는 차량은 무시한다")
        void it_ignores_non_existing_car() {
            // given
            Car savedCar = Car.from("pobi");
            carRepository.saveAll(List.of(savedCar));

            Car newCar = Car.from("woni");
            newCar.moveForward();

            // when
            carRepository.updateAll(List.of(newCar));

            // then
            List<Car> cars = carRepository.findAll();
            assertThat(cars).hasSize(1);
            assertThat(cars.getFirst().getName()).isEqualTo("pobi");
        }
    }
}
