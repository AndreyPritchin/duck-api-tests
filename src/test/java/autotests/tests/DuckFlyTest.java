package autotests.tests;

import autotests.EndpointConfig;
import autotests.clients.*;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import payloads.DuckCreatePayload;
import payloads.DuckValidationMessagePayload;

@ContextConfiguration(classes = {EndpointConfig.class, DuckFlyClient.class})
public class DuckFlyTest extends DuckFlyClient {

    @Test(description = "Проверка характеристик утки с активным состоянием крыльев")
    @CitrusTest
    public void testGetFlyActiveDuck(@Optional @CitrusResource TestCaseRunner runner) {

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

        String duckId = idExtract(runner);

        //Вызов метода полета утки
        getFlyDuck(runner, "${duckId}");

        //Вызов метода для создания параметров утки payload
        DuckValidationMessagePayload expectedDuck = new DuckValidationMessagePayload()
                .message("I am flying :)");

        //Валидация ответа payload
        validationJsonPayload(runner, HttpStatus.OK, expectedDuck);
    }

    @Test(description = "Проверка характеристик утки со связанными крыльями")
    @CitrusTest
    public void testGetFlyFixedDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания параметров утки payload
        DuckCreatePayload duckCreatePayload = new DuckCreatePayload()
                .id("@ignore@")
                .color("yellow")
                .height(10.0)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");
        //Вызов метода для создания утки payload
        createDuckPayload(runner, duckCreatePayload);

        String duckId = idExtract(runner);

        //Вызов метода полета утки
        getFlyDuck(runner, "${duckId}");

        //Вызов метода для создания параметров утки payload
        DuckValidationMessagePayload expectedDuck = new DuckValidationMessagePayload()
                .message("I can not fly :C");

        //Валидация ответа payload
        validationJsonPayload(runner, HttpStatus.OK, expectedDuck);
    }

    @Test(description = "Проверка характеристик утки с неопределенным состоянием крыльев")
    @CitrusTest
    public void testGetFlyUndefinedDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания параметров утки payload
        DuckCreatePayload duckCreatePayload = new DuckCreatePayload()
                .id("@ignore@")
                .color("yellow")
                .height(10.0)
                .material("rubber")
                .sound("quack")
                .wingsState("UNDEFINED");
        //Вызов метода для создания утки payload
        createDuckPayload(runner, duckCreatePayload);

        String duckId = idExtract(runner);

        //Вызов метода полета утки
        getFlyDuck(runner, "${duckId}");

        //Вызов метода для создания параметров утки payload
        DuckValidationMessagePayload expectedDuck = new DuckValidationMessagePayload()
                .message("Wings are not detected :(");

        //Валидация ответа payload
        validationJsonPayload(runner, HttpStatus.OK, expectedDuck);
    }
}