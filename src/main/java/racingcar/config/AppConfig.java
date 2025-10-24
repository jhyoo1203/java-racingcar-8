package racingcar.config;

import racingcar.application.CarService;
import racingcar.application.RacingService;
import racingcar.domain.repository.CarRepository;
import racingcar.domain.repository.RacingRepository;
import racingcar.presentation.RacingController;
import racingcar.view.InputView;
import racingcar.view.OutputView;

public class AppConfig {

    // 뷰 DI
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    // 레포지토리 DI
    private final CarRepository carRepository = new CarRepository();
    private final RacingRepository racingRepository = new RacingRepository();

    // 서비스 DI
    private final CarService carService = new CarService(carRepository);
    private final RacingService racingService = new RacingService(racingRepository, carService);

    // 컨트롤러 DI
    private final RacingController racingController = new RacingController(
            inputView,
            outputView,
            carService,
            racingService
    );

    public RacingController getRacingController() {
        return racingController;
    }
}
