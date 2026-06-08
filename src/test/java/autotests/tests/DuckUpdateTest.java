package autotests.tests;

import autotests.EndpointConfig;
import autotests.clients.DuckUpdateClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@ContextConfiguration(classes = {EndpointConfig.class, DuckUpdateClient.class})
public class DuckUpdateTest extends DuckUpdateClient {

    @Test(description = "Проверка обновления цвета и высоты утки")
    @CitrusTest
    public void testUpdateHeightDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания утки
        createDuck(runner, "yellow", 10.0, "wood", "quack", "ACTIVE");

        String duckId = idExtract(runner);

        //Вызов метода для обновления утки
        updateDuck(runner, "${duckId}", "red", 5, "wood", "quack", "ACTIVE");

        //Валидация ответа
        validationJson(runner, HttpStatus.OK, "{\n" +
                "  \"message\": \"Duck with id = ${duckId} is updated\"\n" +
                "}");
    }

    @Test(description = "Проверка обновления цвета и звука утки")
    @CitrusTest
    public void testUpdateSoundDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания утки
        createDuck(runner, "yellow", 10.0, "wood", "quack", "ACTIVE");

        String duckId = idExtract(runner);

        //Вызов метода для обновления утки
        updateDuck(runner, "${duckId}", "green", 10.0, "wood", "KuKu", "ACTIVE");

        //Валидация ответа
        validationJson(runner, HttpStatus.OK, "{\n" +
                "  \"message\": \"Duck with id = ${duckId} is updated\"\n" +
                "}");
    }
}