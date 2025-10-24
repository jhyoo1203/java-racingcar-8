package racingcar.application.dto;

import java.util.List;
import java.util.regex.Pattern;

public record CarsSaveRequest(
        List<String> carNames
) {
    private static final Pattern CAR_NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]{1,5}$");

    public static CarsSaveRequest from(List<String> carNames) {
        validateCarNames(carNames);
        return new CarsSaveRequest(carNames);
    }

    private static void validateCarNames(List<String> carNames) {
        if (carNames == null || carNames.isEmpty()) {
            throw new IllegalArgumentException("차량 목록이 비어 있습니다.");
        }

        for (String name : carNames) {
            if (!CAR_NAME_PATTERN.matcher(name).matches()) {
                throw new IllegalArgumentException(String.format("차 이름 형식이 올바르지 않습니다. 이름: %s", name));
            }
        }

        long uniqueCount = carNames.stream()
                .distinct()
                .count();

        if (uniqueCount != carNames.size()) {
            throw new IllegalArgumentException("차 이름이 중복되었습니다.");
        }
    }
}
