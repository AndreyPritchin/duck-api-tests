package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})

public class DuckQuackClient extends DuckClient{

    @Autowired
    protected HttpClient duckService;

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
}
