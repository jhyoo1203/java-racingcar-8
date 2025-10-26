package racingcar.application.dto;

import racingcar.util.CollectionUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public record CarsSaveRequest(
        List<String> carNames
) {

    private static final int MIN_CAR_COUNT = 2;
    private static final int MAX_CAR_COUNT = 10;
    private static final Pattern CAR_NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]{1,5}$");

    public CarsSaveRequest {
        validate(carNames);
        carNames = List.copyOf(carNames);
    }

    public static CarsSaveRequest from(List<String> carNames) {
        return new CarsSaveRequest(carNames);
    }

    private void validate(List<String> carNames) {
        validateCount(carNames);
        validateNameFormat(carNames);
    }

    private void validateCount(List<String> carNames) {
        if (CollectionUtil.isEmpty(carNames)) {
            throw new IllegalArgumentException("차량 목록이 비어 있습니다.");
        }

        if (carNames.size() < MIN_CAR_COUNT || carNames.size() > MAX_CAR_COUNT) {
            throw new IllegalArgumentException(String.format("차량 수는 %d 이상 %d 이하이어야 합니다.", MIN_CAR_COUNT, MAX_CAR_COUNT));
        }
    }

    private void validateNameFormat(List<String> carNames) {
        Set<String> uniqueNames = new HashSet<>();

        for (String name : carNames) {
            // 1. 형식 검사
            if (!CAR_NAME_PATTERN.matcher(name).matches()) {
                throw new IllegalArgumentException(String.format("차 이름 형식이 올바르지 않습니다. 이름: %s", name));
            }

            // 2. 중복 검사
            if (!uniqueNames.add(name)) {
                throw new IllegalArgumentException("차 이름이 중복되었습니다.");
            }
        }
    }
}
