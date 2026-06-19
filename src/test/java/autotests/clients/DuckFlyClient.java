package autotests.clients;

import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;

public class DuckFlyClient extends DuckClient{

    //Метод полета утки
    @Step("Метод полета утки")
    public void getFlyDuck(TestCaseRunner runner, String id) {
        getMethod(runner, duckService, "/api/duck/action/fly", "id", id);
    }
}