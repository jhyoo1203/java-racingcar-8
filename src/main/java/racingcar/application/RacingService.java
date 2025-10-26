package racingcar.application;

import racingcar.application.dto.TryCountSaveRequest;
import racingcar.domain.Car;
import racingcar.domain.repository.RacingRepository;
import racingcar.util.MoveCondition;

import java.util.List;

public class RacingService {

    private final RacingRepository racingRepository;
    private final CarService carService;

    public RacingService(RacingRepository racingRepository, CarService carService) {
        this.racingRepository = racingRepository;
        this.carService = carService;
    }

    public void saveTryCount(TryCountSaveRequest request) {
        racingRepository.saveTryCount(request.tryCount());
    }

    public String race() {
        StringBuilder result = new StringBuilder();
        List<Car> cars = carService.getCars();
        int tryCount = racingRepository.findTryCount();

        for (int i = 0; i < tryCount; i++) {
            attemptMove(cars);

            // 자동차 현황 문자열로 표현하여 StringBuilder에 추가
            result.append(carService.carsStatusToString(cars));
        }

        return result.toString();
    }

    private void attemptMove(List<Car> cars) {
        cars.forEach(car -> {
            if (MoveCondition.canMove()) {
                car.moveForward();
            }
        });

        carService.updateCars(cars);
    }

    public List<String> getWinners() {
        List<Car> cars = carService.getCars();

        int maxPosition = cars.stream()
                .mapToInt(Car::getPosition)
                .max()
                .orElseThrow(() -> new IllegalArgumentException("자동차 목록이 비어 있습니다."));

        return cars.stream()
                .filter(car -> car.getPosition() == maxPosition)
                .map(Car::getName)
                .toList();
    }
}
