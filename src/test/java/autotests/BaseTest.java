package autotests;

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
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class BaseTest extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient duckService;

    @Autowired
    protected SingleConnectionDataSource testDb;


    //Методы БД

    //Метод изменения через БД
    @Step("Метод изменения через БД")
    public void dataBaseUpdate(TestCaseRunner runner, String sql) {
        runner.$(sql(testDb)
                .statement(sql));
    }


    //Методы API

    //Метод post String
    @Step("Метод post String")
    public void postMethodString(TestCaseRunner runner, HttpClient service, String apiPath, String bodyString) {
        runner.$(http()
                .client(service)
                .send()
                .post(apiPath)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(bodyString));
    }

    //Метод post Payload
    @Step("Метод post Payload")
    public void postMethodPayload(TestCaseRunner runner, HttpClient service, String apiPath, Object duckCreatePayload) {
        runner.$(http()
                .client(service)
                .send()
                .post(apiPath)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(duckCreatePayload, new ObjectMapper())));
    }

    //Метод delete
    @Step("Метод delete")
    public void deleteMethodString(TestCaseRunner runner, HttpClient service, String apiPath, String queryName, String queryValue) {
        runner.$(http()
                .client(service)
                .send()
                .delete(apiPath)
                .queryParam(queryName, queryValue));
    }

    //Метод get
    @Step("Метод get")
    public void getMethod(TestCaseRunner runner, HttpClient service, String apiPath, String queryName, String queryValue) {
        runner.$(http()
                .client(service)
                .send()
                .get(apiPath)
                .queryParam(queryName, queryValue));
    }


    //Методы валидации

    //Общий метод валидации статус-кода и json-сообщения string
    @Step("Общий метод валидации статус-кода и json-сообщения string")
    public void validationJsonStringBase(TestCaseRunner runner, HttpClient service, HttpStatus statusCode, String jsonMessage) {
        runner.$(http()
                .client(service)
                .receive()
                .response(statusCode)
                .message()
                .type(MessageType.JSON)
                .body(jsonMessage));
    }

    //Общий метод валидации статус-кода и json-сообщения resources
    @Step("Общий метод валидации статус-кода и json-сообщения resources")
    public void validationJsonResourcesBase(TestCaseRunner runner, HttpClient service, HttpStatus statusCode, String expectedResources) {
        runner.$(http()
                .client(service)
                .receive()
                .response(statusCode)
                .message()
                .type(MessageType.JSON)
                .body(new ClassPathResource(expectedResources)));
    }

    //Общий метод валидации статус-кода и json-сообщения payload
    @Step("Общий метод валидации статус-кода и json-сообщения payload")
    public void validationJsonPayloadBase(TestCaseRunner runner, HttpClient service, HttpStatus statusCode, Object expectedPayload) {
        runner.$(http()
                .client(service)
                .receive()
                .response(statusCode)
                .message()
                .type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(expectedPayload, new ObjectMapper())));
    }

    //Общий метод валидации только статус-кода
    @Step("Общий метод валидации только статус-кода")
    public void validationStatusBase(TestCaseRunner runner, HttpClient service, HttpStatus statusCode) {
        runner.$(http()
                .client(service)
                .receive()
                .response(statusCode)
                .message());
    }
}
