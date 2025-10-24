package racingcar;

import racingcar.config.AppConfig;
import racingcar.presentation.RacingController;

public class Application {
    public static void main(String[] args) {
        AppConfig config = new AppConfig();
        RacingController racingController = config.getRacingController();

        racingController.start();
    }
}
