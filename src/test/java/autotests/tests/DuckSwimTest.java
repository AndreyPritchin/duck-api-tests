package autotests.tests;

import autotests.EndpointConfig;
import autotests.clients.DuckActionsClient;
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

@ContextConfiguration(classes = {EndpointConfig.class, DuckControllerClient.class, DuckValidationClient.class, IdExtractClient.class, DuckActionsClient.class})
public class DuckSwimTest extends TestNGCitrusSpringSupport {

    @Autowired
    private DuckControllerClient duckControllerClient;
    @Autowired
    private DuckValidationClient duckValidationClient;
    @Autowired
    private IdExtractClient idExtractClient;
    @Autowired
    private DuckActionsClient duckActionsClient;

    @Test(description = "Проверка поплыва утки с существующим id")
    @CitrusTest
    public void testGetExistentDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания утки
        duckControllerClient.createDuck(runner, "yellow", 10.0, "wood", "quack", "ACTIVE");

        String duckId = idExtractClient.idExtract(runner);

        //Вызов метода поплыва утки
        duckActionsClient.getSwimDuck(runner, duckId);

        //Валидация ответа
        duckValidationClient.validationStatus(runner, HttpStatus.BAD_REQUEST);
    }
    //По результатам тестов: Swim выдает ошибку BAD_REQUEST для существующего ID

    @Test(description = "Проверка поплыва утки с НЕсуществующим id")
    @CitrusTest
    public void testGetNonExistentDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода поплыва утки
        duckActionsClient.getSwimDuck(runner, "-1");

        //Валидация ответа
        duckValidationClient.validationStatus(runner, HttpStatus.NOT_FOUND);
    }
}
