package autotests.clients;

import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;

import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckDeleteClient extends DuckClient{

    //Метод для удаления утки
    @Step("Метод для удаления утки")
    public void deleteDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(duckService)
                .send()
                .delete("/api/duck/delete")
                .queryParam("id", id));
    }

    //Метод проверки удаления через БД
    @Step("Метод проверки удаления через БД")
    public void validateDeletedDatabase(TestCaseRunner runner, String id) {
        runner.$(query(testDb)
                .statement("select COUNT(1) as DUCK_COUNT from duck where ID = " + id)
                .validate("DUCK_COUNT", "0"));
    }
}
