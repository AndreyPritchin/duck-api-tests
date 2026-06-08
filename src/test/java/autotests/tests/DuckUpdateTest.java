package autotests.tests;

import autotests.clients.*;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import payloads.DuckCreatePayload;
import payloads.DuckValidationMessagePayload;

public class DuckUpdateTest extends DuckUpdateClient {

    @Test(description = "Проверка обновления цвета и высоты утки")
    @CitrusTest
    public void testUpdateHeightDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания параметров утки
        DuckCreatePayload duckCreatePayload = new DuckCreatePayload()
                .color("yellow")
                .height(10.0)
                .material("wood")
                .sound("quack")
                .wingsState("ACTIVE");
        //Вызов метода для создания утки payload
        createDuckPayload(runner, duckCreatePayload);

        String duckId = idExtract(runner);

        //Вызов метода для обновления утки
        updateDuck(runner, "${duckId}", "red", 5, "wood", "quack", "ACTIVE");

        //Валидация ответа resources
        validationJsonResources(runner, HttpStatus.OK, "UpdateDuckTestResources/updateDuck.json");
    }

    @Test(description = "Проверка обновления цвета и звука утки")
    @CitrusTest
    public void testUpdateSoundDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания параметров утки
        DuckCreatePayload duckCreatePayload = new DuckCreatePayload()
                .color("yellow")
                .height(10.0)
                .material("wood")
                .sound("quack")
                .wingsState("ACTIVE");
        //Вызов метода для создания утки payload
        createDuckPayload(runner, duckCreatePayload);

        String duckId = idExtract(runner);

        //Вызов метода для обновления утки
        updateDuck(runner, "${duckId}", "green", 10.0, "wood", "KuKu", "ACTIVE");

        //Вызов метода для создания параметров утки payload
        DuckValidationMessagePayload expectedDuck = new DuckValidationMessagePayload()
                .message("Duck with id = ${duckId} is updated");

        //Валидация ответа payload
        validationJsonPayload(runner, HttpStatus.OK, expectedDuck);
    }
}