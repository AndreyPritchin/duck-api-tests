package autotests.clients;

import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;

public class DuckSwimClient extends DuckClient{

    //Метод поплыва утки
    @Step("Метод поплыва утки")
    public void getSwimDuck(TestCaseRunner runner, String id) {
        getMethod(runner, duckService, "/api/duck/action/swim", "id", id);
    }
}
