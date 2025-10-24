package racingcar.view;

import camp.nextstep.edu.missionutils.Console;

import java.util.List;

public class InputView {

    public List<String> readCarNames() {
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        return Serializer.splitByComma(Console.readLine());
    }

    public int readTryCount() {
        System.out.println("시도할 회수는 몇 회인가요?");
        return Serializer.parseInt(Console.readLine());
    }
}
