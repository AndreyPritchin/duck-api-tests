package autotests.clients;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;
import org.springframework.http.HttpStatus;

import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;

public class DuckClient extends BaseTest {


    //Методы БД

    //Метод создания утки через БД
    @Step("Метод создания утки через БД")
    public void dataBaseDuckCreate(TestCaseRunner runner, String duckId, String color, String height, String material, String sound, String wingsState) {
        dataBaseUpdate(runner, "insert into duck (id, color, height, material, sound, wings_state)\n"+
                "values (${duckId}, '" +color+ "', " +height+ ", '" +material+ "', '" +sound+ "', '" +wingsState+ "');");
    }

    //Метод удаления утки через БД
    @Step("Метод удаления утки через БД")
    public void dataBaseDuckDelete(TestCaseRunner runner, String duckId) {
        dataBaseUpdate(runner, "delete from duck where ID = ${duckId}");
    }

    //Метод валидации значений через БД
    @Step("Метод валидации значений через БД")
    protected void validationDatabase(TestCaseRunner runner, String id, String color, String height, String material, String sound, String wingsState) {
        runner.$(query(testDb)
                .statement("select * from duck where ID = " + id)
                .validate("COLOR", color)
                .validate("HEIGHT", height)
                .validate("MATERIAL", material)
                .validate("SOUND", sound)
                .validate("WINGS_STATE", wingsState));
    }

    //Метод проверки удаления через БД
    @Step("Метод проверки удаления через БД")
    public void validateDeletedDatabase(TestCaseRunner runner, String id) {
        runner.$(query(testDb)
                .statement("select COUNT(1) as DUCK_COUNT from duck where ID = " + id)
                .validate("DUCK_COUNT", "0"));
    }


    //Методы валидации

    //Метод валидации статус-кода и json-сообщения string
    @Step("Метод валидации статус-кода и json-сообщения string")
    public void validationJsonString(TestCaseRunner runner, HttpStatus statusCode, String jsonMessage) {
        validationJsonStringBase(runner, duckService, statusCode, jsonMessage);
    }

    //Метод валидации статус-кода и json-сообщения resources
    @Step("Метод валидации статус-кода и json-сообщения resources")
    public void validationJsonResources(TestCaseRunner runner, HttpStatus statusCode, String expectedResources) {
        validationJsonResourcesBase(runner, duckService, statusCode, expectedResources);
    }

    //Метод валидации статус-кода и json-сообщения payload
    @Step("Метод валидации статус-кода и json-сообщения payload")
    public void validationJsonPayload(TestCaseRunner runner, HttpStatus statusCode, Object expectedPayload) {
        validationJsonPayloadBase(runner, duckService, statusCode, expectedPayload);
    }

    //Метод валидации только статус-кода
    @Step("Метод валидации только статус-кода")
    public void validationStatus(TestCaseRunner runner, HttpStatus statusCode) {
        validationStatusBase(runner, duckService, statusCode);
    }

    //Общий метод валидации статус-кода и ПУСТОГО json-сообщения
    public void validateJsonEmpty(TestCaseRunner runner, HttpStatus statusCode) {
        validateJsonEmptyBase(runner, duckService, statusCode);
    }
}
