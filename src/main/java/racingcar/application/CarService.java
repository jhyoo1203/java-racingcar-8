package racingcar.application;

import racingcar.application.dto.CarsSaveRequest;
import racingcar.domain.Car;
import racingcar.domain.repository.CarRepository;
import racingcar.util.CollectionUtil;

import java.util.List;

public class CarService {

    private static final String NEW_LINE = "\n";
    private static final String CAR_STATUS_BAR = "-";
    private static final String CAR_STATUS_DELIMITER = " : ";

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void saveCars(CarsSaveRequest request) {
        List<String> carNames = request.carNames();

        carRepository.saveAll(
                carNames.stream()
                        .map(Car::from)
                        .toList()
        );
    }

    public List<Car> getCars() {
        return carRepository.findAll();
    }

    public void updateCars(List<Car> cars) {
        if (CollectionUtil.isEmpty(cars)) {
            throw new IllegalArgumentException("업데이트할 차량 목록이 비어 있습니다.");
        }

        carRepository.updateAll(cars);
    }

    public String carsStatusToString(List<Car> cars) {
        StringBuilder sb = new StringBuilder();

        for (Car car : cars) {
            sb.append(car.getName())
                    .append(CAR_STATUS_DELIMITER)
                    .append(CAR_STATUS_BAR.repeat(car.getPosition()))
                    .append(NEW_LINE);
        }
        sb.append(NEW_LINE);

        return sb.toString();
    }
}
