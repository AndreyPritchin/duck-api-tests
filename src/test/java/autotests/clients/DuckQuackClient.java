package autotests.clients;

import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckQuackClient extends DuckClient{

    //Метод кряканья утки
    @Step("Метод кряканья утки")
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
