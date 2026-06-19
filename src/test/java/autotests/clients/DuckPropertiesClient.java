package autotests.clients;

import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckPropertiesClient extends DuckClient {

    //Метод для показа характеристик утки
    @Step("Метод для показа характеристик утки")
    public void getPropertiesDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(duckService)
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", id));
    }
}
