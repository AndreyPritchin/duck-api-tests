package autotests.tests;

import autotests.EndpointConfig;
import autotests.clients.DuckControllerClient;
import autotests.clients.DuckValidationClient;
import autotests.clients.IdExtractClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@ContextConfiguration(classes = {EndpointConfig.class, DuckControllerClient.class, DuckValidationClient.class, IdExtractClient.class})
public class DuckUpdateTest extends TestNGCitrusSpringSupport {

    @Autowired
    private DuckControllerClient duckControllerClient;
    @Autowired
    private DuckValidationClient duckValidationClient;
    @Autowired
    private IdExtractClient idExtractClient;

    @Test(description = "Проверка обновления цвета и высоты утки")
    @CitrusTest
    public void testUpdateHeightDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания утки
        duckControllerClient.createDuck(runner, "yellow", 10.0, "wood", "quack", "ACTIVE");

        String duckId = idExtractClient.idExtract(runner);

        //Вызов метода для обновления утки
        duckControllerClient.updateDuck(runner, "${duckId}", "red", 5, "wood", "quack", "ACTIVE");

        //Валидация ответа
        duckValidationClient.validationJson(runner, HttpStatus.OK, "{\n" +
                "  \"message\": \"Duck with id = ${duckId} is updated\"\n" +
                "}");
    }

    @Test(description = "Проверка обновления цвета и звука утки")
    @CitrusTest
    public void testUpdateSoundDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания утки
        duckControllerClient.createDuck(runner, "yellow", 10.0, "wood", "quack", "ACTIVE");

        String duckId = idExtractClient.idExtract(runner);

        //Вызов метода для обновления утки
        duckControllerClient.updateDuck(runner, "${duckId}", "green", 10.0, "wood", "KuKu", "ACTIVE");

        //Валидация ответа
        duckValidationClient.validationJson(runner, HttpStatus.OK, "{\n" +
                "  \"message\": \"Duck with id = ${duckId} is updated\"\n" +
                "}");
    }
}