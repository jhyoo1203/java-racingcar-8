package racingcar.presentation;

import racingcar.application.CarService;
import racingcar.application.RacingService;
import racingcar.application.dto.CarsSaveRequest;
import racingcar.application.dto.TryCountSaveRequest;
import racingcar.view.InputView;
import racingcar.view.OutputView;

import java.util.List;

public class RacingController {

    private final InputView inputView;
    private final OutputView outputView;
    private final CarService carService;
    private final RacingService racingService;

    public RacingController(InputView inputView,
                            OutputView outputView,
                            CarService carService,
                            RacingService racingService
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.carService = carService;
        this.racingService = racingService;
    }

    public void start() {
        raceInit();
        outputView.printRaceProcess(racingService.race());
        outputView.printWinners(racingService.getWinners());
    }

    private void raceInit() {
        saveCars();
        saveTryCount();
    }

    private void saveCars() {
        List<String> carNames = inputView.readCarNames();
        carService.saveCars(CarsSaveRequest.from(carNames));
    }

    private void saveTryCount() {
        int tryCount = inputView.readTryCount();
        racingService.saveTryCount(TryCountSaveRequest.from(tryCount));
    }
}
