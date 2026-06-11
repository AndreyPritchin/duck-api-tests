package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckFlyClient extends DuckClient{

    //Метод полета утки
    @Step("Метод полета утки")
    public void getFlyDuck(TestCaseRunner runner, String id) {
        getMethod(runner, duckService, "/api/duck/action/fly", "id", id);
    }
}