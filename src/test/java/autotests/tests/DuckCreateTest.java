package autotests.tests;

import autotests.EndpointConfig;
import autotests.clients.DuckControllerClient;
import autotests.clients.DuckValidationClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@ContextConfiguration(classes = {EndpointConfig.class, DuckControllerClient.class, DuckValidationClient.class})
public class DuckCreateTest extends TestNGCitrusSpringSupport {

    @Autowired
    private DuckControllerClient duckControllerClient;
    @Autowired
    private DuckValidationClient duckValidationClient;

    @Test(description = "Проверка создания утки с материалом rubber")
    @CitrusTest
    public void testCreateRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания утки
        duckControllerClient.createDuck(runner, "yellow", 10.0, "rubber", "quack", "ACTIVE");

        //Валидация ответа
        duckValidationClient.validationJson(runner, HttpStatus.OK, "{\n" +
                "  \"id\": \"@ignore@\",\n" +
                "  \"color\": \"yellow\",\n" +
                "  \"height\": 10.0,\n" +
                "  \"material\": \"rubber\",\n" +
                "  \"sound\": \"quack\",\n" +
                "  \"wingsState\": \"ACTIVE\"\n" +
                "}");
    }

    @Test(description = "Проверка создания утки с материалом wood")
    @CitrusTest
    public void testCreateWoodDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания утки
        duckControllerClient.createDuck(runner, "yellow", 10.0, "wood", "quack", "ACTIVE");

        //Валидация ответа
        duckValidationClient.validationJson(runner, HttpStatus.OK, "{\n" +
                "  \"id\": \"@ignore@\",\n" +
                "  \"color\": \"yellow\",\n" +
                "  \"height\": 10.0,\n" +
                "  \"material\": \"wood\",\n" +
                "  \"sound\": \"quack\",\n" +
                "  \"wingsState\": \"ACTIVE\"\n" +
                "}");
    }
}