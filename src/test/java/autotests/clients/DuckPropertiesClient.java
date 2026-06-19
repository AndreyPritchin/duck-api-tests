package autotests.clients;

import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;

public class DuckPropertiesClient extends DuckClient {

    //Метод для показа характеристик утки
    @Step("Метод для показа характеристик утки")
    public void getPropertiesDuck(TestCaseRunner runner, String id) {
        getMethod(runner, duckService, "/api/duck/action/properties", "id", id);
    }

}
