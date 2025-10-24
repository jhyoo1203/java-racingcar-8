package racingcar.domain.repository;

import racingcar.domain.Car;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarRepository {

    private static final Map<String, Car> carMap = new HashMap<>();

    public void saveAll(List<Car> cars) {
        for (Car car : cars) {
            carMap.put(car.getName(), car);
        }
    }

    public List<Car> findAll() {
        return List.copyOf(carMap.values());
    }

    public void updateAll(List<Car> cars) {
        for (Car car : cars) {
            carMap.put(car.getName(), car);
        }
    }
}
