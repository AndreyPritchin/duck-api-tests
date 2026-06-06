package autotests.tests;

import autotests.EndpointConfig;
import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import payloads.DuckCreatePayload;
import payloads.DuckValidationParametersPayload;

@ContextConfiguration(classes = {EndpointConfig.class, DuckClient.class})
public class DuckCreateTest extends DuckClient {

    @Test(description = "Проверка создания утки с материалом rubber. Валидация string")
    @CitrusTest
    public void testCreateRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания утки string
        //createDuck(runner, "yellow", 10.0, "rubber", "quack", "ACTIVE");

        //Вызов метода для создания параметров утки payload
        DuckCreatePayload duckCreatePayload = new DuckCreatePayload()
                .id("@ignore@")
                .color("yellow")
                .height(10.0)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");
        //Вызов метода для создания утки payload
        createDuckPayload(runner, duckCreatePayload);

        /*
        //Валидация ответа string
        validationJsonString(runner, HttpStatus.OK, "{\n" +
                "  \"id\": \"@ignore@\",\n" +
                "  \"color\": \"yellow\",\n" +
                "  \"height\": 10.0,\n" +
                "  \"material\": \"rubber\",\n" +
                "  \"sound\": \"quack\",\n" +
                "  \"wingsState\": \"ACTIVE\"\n" +
                "}");*/

        //Вызов метода для создания параметров утки payload
        DuckValidationParametersPayload expectedDuck = new DuckValidationParametersPayload()
                .id("@ignore@")
                .color("yellow")
                .height(10.0)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");

        //Валидация ответа payload
        validationJsonPayload(runner, HttpStatus.OK, expectedDuck);
    }

    @Test(description = "Проверка создания утки с материалом wood")
    @CitrusTest
    public void testCreateWoodDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания утки string
        //createDuckString(runner, "yellow", 10.0, "wood", "quack", "ACTIVE");

        //Вызов метода для создания параметров утки
        DuckCreatePayload duckCreatePayload = new DuckCreatePayload()
                .color("yellow")
                .height(10.0)
                .material("wood")
                .sound("quack")
                .wingsState("ACTIVE");
        //Вызов метода для создания утки payload
        createDuckPayload(runner, duckCreatePayload);

        //Валидация ответа resources
        validationJsonResources(runner, HttpStatus.OK, "CreateDuckTestResources/createWoodDuck.json");
    }
}