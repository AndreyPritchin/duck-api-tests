package autotests.tests;

import autotests.EndpointConfig;
import autotests.clients.DuckActionsClient;
import autotests.clients.DuckControllerClient;
import autotests.clients.DuckValidationClient;
import autotests.clients.IdExtractClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class, DuckControllerClient.class, DuckActionsClient.class, DuckValidationClient.class, IdExtractClient.class})
public class DuckSwimTest extends TestNGCitrusSpringSupport {

    @Autowired
    private DuckControllerClient duckControllerClient;
    @Autowired
    private DuckActionsClient duckActionsClient;
    @Autowired
    private DuckValidationClient duckValidationClient;
    @Autowired
    private IdExtractClient idExtractClient;

    @Test(description = "Проверка поплыва утки с существующим id")
    @CitrusTest
    public void testGetExistentDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания утки
        duckControllerClient.createDuck(runner, "yellow", 10.0, "wood", "quack", "ACTIVE");

        String duckId = idExtractClient.idExtract(runner);

        //Вызов метода полета утки
        duckActionsClient.getSwimDuck(runner, duckId);

        //Валидация ответа
        duckValidationClient.validationJson(runner, HttpStatus.BAD_REQUEST, "{\n" +
                "  \"message\": \"I'm swimming\"\n" +
                "}");

    }

    /*@Test(description = "Проверка поплыва утки с НЕсуществующим id")
    @CitrusTest
    public void testGetNonExistentDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания утки
        createDuck(runner, "yellow", 10.0, "wood", "quack", "ACTIVE");

        //Валидация JSON-ответа
        runner.$(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .body("{\n" +
                        "  \"id\": \"@ignore@\",\n" +
                        "  \"color\": \"yellow\",\n" +
                        "  \"height\": 10.0,\n" +
                        "  \"material\": \"wood\",\n" +
                        "  \"sound\": \"quack\",\n" +
                        "  \"wingsState\": \"ACTIVE\"\n" +
                        "}")
                //Записываем id в переменную duckId
                .extract(fromBody().expression("$.id", "duckId")));

        int duckIdInt = Integer.parseInt("duckId");
        duckIdInt++;
        String duckId = String.valueOf(duckIdInt);

        //Вызов метода поплыва утки
        getSwimDuck(runner, "${duckId}");

        //Валидация ответа
        runner.$(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.BAD_REQUEST));
    }
    //По результатам тестов: Swim выдает ошибку NOT_FOUND*/
}
