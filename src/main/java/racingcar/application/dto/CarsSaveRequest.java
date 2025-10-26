package racingcar.application.dto;

import racingcar.util.CollectionUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public record CarsSaveRequest(
        List<String> carNames
) {
    private static final Pattern CAR_NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]{1,5}$");

    public CarsSaveRequest(List<String> carNames) {
        validate(carNames);
        this.carNames = List.copyOf(carNames);
    }

    public static CarsSaveRequest from(List<String> carNames) {
        return new CarsSaveRequest(carNames);
    }

    private void validate(List<String> carNames) {
        if (CollectionUtil.isEmpty(carNames)) {
            throw new IllegalArgumentException("차량 목록이 비어 있습니다.");
        }

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
