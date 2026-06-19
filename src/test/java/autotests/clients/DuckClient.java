package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckClient extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient duckService;

    @Autowired
    protected SingleConnectionDataSource testDb;


    //Методы валидации


    //Метод валидации статус-кода и json-сообщения string
    @Step("Метод валидации статус-кода и json-сообщения string")
    public void validationJsonString(TestCaseRunner runner, HttpStatus statusCode, String jsonMessage) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(statusCode)
                .message()
                .type(MessageType.JSON)
                .body(jsonMessage));
    }

    //Метод валидации статус-кода и json-сообщения resources
    @Step("Метод валидации статус-кода и json-сообщения resources")
    public void validationJsonResources(TestCaseRunner runner, HttpStatus statusCode, String expectedResources) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(statusCode)
                .message()
                .type(MessageType.JSON)
                .body(new ClassPathResource(expectedResources)));
    }

    //Метод валидации статус-кода и json-сообщения payload
    @Step("Метод валидации статус-кода и json-сообщения payload")
    public void validationJsonPayload(TestCaseRunner runner, HttpStatus statusCode, Object expectedPayload) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(statusCode)
                .message()
                .type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(expectedPayload, new ObjectMapper())));
    }

    //Метод валидации только статус-кода
    @Step("Метод валидации только статус-кода")
    public void validationStatus(TestCaseRunner runner, HttpStatus statusCode) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(statusCode)
                .message());
    }

    //Метод валидации статус-кода и ПУСТОВО json-сообщения
    public void validateJsonEmpty(TestCaseRunner runner, HttpStatus status) {
        runner.$(
                http()
                        .client(duckService)
                        .receive()
                        .response(status)
                        .message()
                        .type(MessageType.PLAINTEXT)
                        .body("")
        );
    }

    //Метод валидации статус-кода и json-сообщения string. Извлечение id
    @Step("Метод валидации статус-кода и json-сообщения string. Извлечение id")
    public String validationJsonStringExtract(TestCaseRunner runner, HttpStatus statusCode, String jsonMessage) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(statusCode)
                .message()
                .type(MessageType.JSON)
                .body(jsonMessage)
                .extract(fromBody().expression("$.id", "duckId")));
        return ("${duckId}");
    }

    //Метод валидации статус-кода и json-сообщения resources. Извлечение id
    @Step("Метод валидации статус-кода и json-сообщения resources. Извлечение id")
    public String validationJsonResourcesExtract(TestCaseRunner runner, HttpStatus statusCode, String expectedResources) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(statusCode)
                .message()
                .type(MessageType.JSON)
                .body(new ClassPathResource(expectedResources))
                .extract(fromBody().expression("$.id", "duckId")));
        return ("${duckId}");
    }

    //Метод валидации статус-кода и json-сообщения payload. Извлечение id
    @Step("Метод валидации статус-кода и json-сообщения payload. Извлечение id")
    public String validationJsonPayloadExtract(TestCaseRunner runner, HttpStatus statusCode, Object expectedPayload) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(statusCode)
                .message()
                .type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(expectedPayload, new ObjectMapper()))
                .extract(fromBody().expression("$.id", "duckId")));
        return ("${duckId}");
    }


    //Методы БД


    //Метод изменения через БД
    @Step("Метод изменения через БД")
    public void dataBaseUpdate(TestCaseRunner runner, String sql) {
        runner.$(sql(testDb)
                .statement(sql));
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
}
