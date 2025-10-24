package racingcar.view;

import java.util.List;

public class OutputView {

    public void printRaceProcess(String raceProcess) {
        System.out.println();
        System.out.println("실행 결과");
        System.out.print(raceProcess);
    }

    public void printWinners(List<String> winners) {
        System.out.println("최종 우승자 : " + String.join(", ", winners));
    }
}
