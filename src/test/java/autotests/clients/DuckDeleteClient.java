package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckDeleteClient extends DuckClient{

    //Метод удаления утки
    @Step("Метод удаления утки")
    public void deleteDuck(TestCaseRunner runner, String id) {
        deleteMethodString(runner, duckService, "/api/duck/delete", "id", id);
    }
}
