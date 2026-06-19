package autotests.clients;

import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;

public class DuckDeleteClient extends DuckClient{

    //Метод удаления утки
    @Step("Метод удаления утки")
    public void deleteDuck(TestCaseRunner runner, String id) {
        deleteMethodString(runner, duckService, "/api/duck/delete", "id", id);
    }
}
