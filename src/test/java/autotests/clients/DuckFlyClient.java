package autotests.clients;

import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckFlyClient extends DuckClient{

    //Метод полета утки
    @Step("Метод полета утки")
    public void getFlyDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(duckService)
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", id));
    }
}