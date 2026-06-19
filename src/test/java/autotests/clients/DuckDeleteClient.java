package autotests.clients;

import com.consol.citrus.TestCaseRunner;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckDeleteClient extends DuckClient{

    //Метод для удаления утки
    public void deleteDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(duckService)
                .send()
                .delete("/api/duck/delete")
                .queryParam("id", id));
    }
}
