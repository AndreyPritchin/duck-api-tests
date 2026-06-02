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

    //Метод поплыва утки
    public void getSwimDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(duckService)
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", id));
    }

    //Метод полета утки
    public void getFlyDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(duckService)
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", id));
    }

    //Метод кряканья утки
    public void getQuackDuck(TestCaseRunner runner, String id, int repetitionCount, int soundCount) {
        runner.$(http()
                .client(duckService)
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", id)
                .queryParam("repetitionCount", String.valueOf(repetitionCount))
                .queryParam("soundCount", String.valueOf(soundCount)));
    }

    //Метод для показа характеристик утки
    public void getPropertiesDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(duckService)
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", id));
    }
}
