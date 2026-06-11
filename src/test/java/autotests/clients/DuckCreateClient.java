package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckCreateClient extends DuckClient {

    //Метод для создания утки String
    @Step("Метод для создания утки String")
    public void createDuckString(TestCaseRunner runner, String bodyString) {
        postMethodPayload(runner, duckService, "/api/duck/create", bodyString);
    }

    //Метод создания утки Payload
    @Step("Метод для создания утки Payload")
    public void createDuckPayload(TestCaseRunner runner, Object duckCreatePayload) {
        postMethodPayload(runner, duckService, "/api/duck/create", duckCreatePayload);
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
        return("duckId");
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
        return("duckId");
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
        return("duckId");
    }
}
