package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckCreateClient extends DuckClient {

    /*
    //Метод для создания утки string
    @Step("Метод для создания утки string")
    public void createDuckString(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        runner.$(http()
                .client(duckService)
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" +
                        "  \"color\": \"" + color + "\",\n" +
                        "  \"height\": " + height + ",\n" +
                        "  \"material\": \"" + material + "\",\n" +
                        "  \"sound\": \"" + sound + "\",\n" +
                        "  \"wingsState\": \"" + wingsState + "\"\n" +
                        "}"));
    }


    //Метод для создания утки payload
    @Step("Метод для создания утки payload")
    public void createDuckPayload(TestCaseRunner runner, Object duckCreatePayload) {
        runner.$(http()
                .client(duckService)
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(duckCreatePayload, new ObjectMapper())));
    }
    */

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
    @Step("Общий метод валидации статус-кода и json-сообщения string")
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
    @Step("Общий метод валидации статус-кода и json-сообщения resources")
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
    @Step("Общий метод валидации статус-кода и json-сообщения payload")
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
