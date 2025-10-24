package racingcar.application.dto;

public record TryCountSaveRequest(
    int tryCount
) {

    private static final int MIN_TRY_COUNT = 1;
    private static final int MAX_TRY_COUNT = 100;

    public static TryCountSaveRequest from(int tryCount) {
        validateTryCount(tryCount);
        return new TryCountSaveRequest(tryCount);
    }

    private static void validateTryCount(int tryCount) {
        if (tryCount < MIN_TRY_COUNT) {
            throw new IllegalArgumentException("시도 횟수는 1 이상이어야 합니다.");
        }

        if (tryCount > MAX_TRY_COUNT) {
            throw new IllegalArgumentException("시도 횟수는 100 이하이어야 합니다.");
        }
    }
}
