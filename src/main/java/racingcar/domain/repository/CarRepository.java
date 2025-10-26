package racingcar.domain.repository;

import racingcar.domain.Car;
import racingcar.util.CollectionUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CarRepository {

    private static final Map<String, Car> carMap = new LinkedHashMap<>();

    public void saveAll(List<Car> cars) {
        if (CollectionUtil.isEmpty(cars)) {
            throw new IllegalArgumentException("저장할 차량 목록이 비어 있습니다.");
        }

        for (Car car : cars) {
            if (isValidCar(car)) {
                carMap.put(car.getName(), car);
            }
        }
    }

    private boolean isValidCar(Car car) {
        return car != null && car.getName() != null;
    }

    public List<Car> findAll() {
        return List.copyOf(carMap.values());
    }

    public void updateAll(List<Car> cars) {
        if (CollectionUtil.isEmpty(cars)) {
            throw new IllegalArgumentException("업데이트할 차량 목록이 비어 있습니다.");
        }

        for (Car car : cars) {
            if (isValidCar(car) && carMap.containsKey(car.getName())) {
                carMap.put(car.getName(), car);
            }
        }
    }
}
