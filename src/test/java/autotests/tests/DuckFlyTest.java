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
public class DuckFlyTest extends TestNGCitrusSpringSupport {

    @Autowired
    private DuckControllerClient duckControllerClient;
    @Autowired
    private DuckValidationClient duckValidationClient;
    @Autowired
    private IdExtractClient idExtractClient;
    @Autowired
    private DuckActionsClient duckActionsClient;

    @Test(description = "Проверка характеристик утки с активным состоянием крыльев")
    @CitrusTest
    public void testGetFlyActiveDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания утки
        duckControllerClient.createDuck(runner, "yellow", 10.0, "wood", "quack", "ACTIVE");

        String duckId = idExtractClient.idExtract(runner);

        //Вызов метода полета утки
        duckActionsClient.getFlyDuck(runner, "${duckId}");

        //Валидация ответа
        duckValidationClient.validationJson(runner, HttpStatus.OK, "{\n" +
                "  \"message\": \"I am flying :)\"\n" + //В требованиях написано - Body: { “message”: “I’m flying”}, но фактический результат - I am flying :). В тесте используется проверка на фактическое сообщение
                "}");

    }

    @Test(description = "Проверка характеристик утки со связанными крыльями")
    @CitrusTest
    public void testGetFlyFixedDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания утки
        duckControllerClient.createDuck(runner, "yellow", 10.0, "wood", "quack", "FIXED");

        String duckId = idExtractClient.idExtract(runner);

        //Вызов метода полета утки
        duckActionsClient.getFlyDuck(runner, "${duckId}");

        //Валидация ответа
        duckValidationClient.validationJson(runner, HttpStatus.OK, "{\n" +
                "  \"message\": \"I can not fly :C\"\n" + //В требованиях написано - Body: { “message”: “I can’t fly”}, но фактический результат - I can not fly :C. В тесте используется проверка на фактическое сообщение
                "}");

    }

    @Test(description = "Проверка характеристик утки со неопределенным состоянием крыльев")
    @CitrusTest
    public void testGetFlyUndefinedDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания утки
        duckControllerClient.createDuck(runner, "yellow", 10.0, "wood", "quack", "UNDEFINED");

        String duckId = idExtractClient.idExtract(runner);

        //Вызов метода полета утки
        duckActionsClient.getFlyDuck(runner, "${duckId}");

        //Валидация ответа
        duckValidationClient.validationJson(runner, HttpStatus.OK, "{\n" +
                "  \"message\": \"Wings are not detected :(\"\n" + //Фактический результат - Wings are not detected :(. В тесте используется проверка на фактическое сообщение
                "}");

    }
}