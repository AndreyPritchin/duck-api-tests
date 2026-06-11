package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckSwimClient extends DuckClient{

    //Метод поплыва утки
    @Step("Метод поплыва утки")
    public void getSwimDuck(TestCaseRunner runner, String id) {
        getMethod(runner, duckService, "/api/duck/action/swim", "id", id);
    }
}
