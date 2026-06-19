package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckClient extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient duckService;


    //Методы controller


    //Метод для создания утки string
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
    public void createDuckPayload(TestCaseRunner runner, Object duckCreatePayload) {
        runner.$(http()
                .client(duckService)
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(duckCreatePayload, new ObjectMapper())));
    }


    //Методы валидации


    //Метод валидации статус-кода и json-сообщения string
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
    public void validationStatus(TestCaseRunner runner, HttpStatus statusCode) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(statusCode)
                .message());
    }

    //Метод валидации статус-кода и ПУСТОГО json-сообщения
    public void validateJsonEmpty(TestCaseRunner runner, HttpStatus statusCode) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(statusCode)
                .message()
                .type(MessageType.JSON)
                .body(""));
    }


    //Метод извлечения id утки и запись его в переменную duckId
    public String idExtract(TestCaseRunner runner) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .extract(fromBody().expression("$.id", "duckId")));
        return("${duckId}");
    }
}
