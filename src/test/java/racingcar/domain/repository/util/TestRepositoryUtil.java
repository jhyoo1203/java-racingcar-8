package racingcar.domain.repository.util;

import racingcar.domain.Car;
import racingcar.domain.repository.CarRepository;
import racingcar.domain.repository.RacingRepository;

import java.lang.reflect.Field;
import java.util.Map;

public class TestRepositoryUtil {

    /**
     * 모든 Repository를 초기화한다
     */
    public static void clearAllRepositories() {
        clearCarRepository();
        clearRacingRepository();
    }

    /**
     * CarRepository의 static carMap을 초기화한다
     */
    @SuppressWarnings("unchecked")
    public static void clearCarRepository() {
        try {
            Field carMapField = CarRepository.class.getDeclaredField("carMap");
            carMapField.setAccessible(true);
            Map<String, Car> carMap = (Map<String, Car>) carMapField.get(null);
            carMap.clear();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clear CarRepository", e);
        }
    }

    /**
     * RacingRepository의 static tryCount를 0으로 초기화한다
     */
    public static void clearRacingRepository() {
        try {
            Field tryCountField = RacingRepository.class.getDeclaredField("tryCount");
            tryCountField.setAccessible(true);
            tryCountField.set(null, 0);
        } catch (Exception e) {
            throw new RuntimeException("Failed to clear RacingRepository", e);
        }
    }
}
