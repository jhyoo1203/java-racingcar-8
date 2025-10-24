package racingcar.view;

import java.util.List;

public final class Serializer {

    private static final String COMMA = ",";

    private Serializer() { }

    public static List<String> splitByComma(String input) {
        return List.of(input.split(COMMA));
    }

    public static int parseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("숫자 형식이 올바르지 않습니다. input: %s", input));
        }
    }
}
