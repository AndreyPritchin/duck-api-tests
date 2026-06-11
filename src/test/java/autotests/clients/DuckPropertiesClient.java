package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckPropertiesClient extends DuckClient {

    //Метод для показа характеристик утки
    @Step("Метод для показа характеристик утки")
    public void getPropertiesDuck(TestCaseRunner runner, String id) {
        getMethod(runner, duckService, "/api/duck/action/properties", "id", id);
    }

}
