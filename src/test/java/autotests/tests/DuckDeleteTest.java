package autotests.tests;

import autotests.clients.*;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.payloads.DuckCreatePayload;

public class DuckDeleteTest extends DuckDeleteClient {

    @Test(description = "Проверка удаления утки")
    @CitrusTest
    public void testDeleteDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания параметров утки payload
        DuckCreatePayload duckCreatePayload = new DuckCreatePayload()
                .color("yellow")
                .height(10.0)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");
        //Вызов метода для создания утки payload
        createDuckPayload(runner, duckCreatePayload);

        String duckId = idExtract(runner);

        //Вызов метода удаления утки
        deleteDuck(runner, duckId);

        //Валидация ответа resources
        validationJsonResources(runner, HttpStatus.OK, "DeleteDuckTestResources/deleteDuck.json");
    }
}
