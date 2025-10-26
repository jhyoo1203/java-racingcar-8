package racingcar.application.dto;

public record TryCountSaveRequest(
        int tryCount
) {

    private static final int MIN_TRY_COUNT = 1;
    private static final int MAX_TRY_COUNT = 100;

    public TryCountSaveRequest {
        validate(tryCount);
    }

    public static TryCountSaveRequest from(int tryCount) {
        return new TryCountSaveRequest(tryCount);
    }

    private void validate(int tryCount) {
        if (tryCount < MIN_TRY_COUNT || tryCount > MAX_TRY_COUNT) {
            throw new IllegalArgumentException(String.format("시도 횟수는 %d 이상 %d 이하이어야 합니다.", MIN_TRY_COUNT, MAX_TRY_COUNT));
        }
    }
}
