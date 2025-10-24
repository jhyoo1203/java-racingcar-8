package racingcar.domain.repository;

public class RacingRepository {

    private static int tryCount;

    public void saveTryCount(int count) {
        tryCount = count;
    }

    public int findTryCount() {
        return tryCount;
    }
}
