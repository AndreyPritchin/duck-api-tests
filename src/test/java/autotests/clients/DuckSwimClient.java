package autotests.clients;

import com.consol.citrus.TestCaseRunner;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckSwimClient extends DuckClient{

    //Метод поплыва утки
    public void getSwimDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(duckService)
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", id));
    }
}
