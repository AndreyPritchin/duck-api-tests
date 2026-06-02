package autotests.clients;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.consol.citrus.http.client.HttpClient;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@Component
public class DuckActionsClient extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient duckService;

    //Создание метода для поплыва утки
    public void getSwimDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(duckService)
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", id));
    }

    //Создание метода для полета утки
    public void getFlyDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(duckService)
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", id));
    }
}
