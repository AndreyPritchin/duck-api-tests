package autotests.tests;

import autotests.clients.DuckCreateClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckCreateTest extends DuckCreateClient {

    @Test(description = "Проверка создания утки с материалом rubber")
    @CitrusTest
    public void testCreateRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания утки
        createDuck(runner, "yellow", 10.0, "rubber", "quack", "ACTIVE");

        //Валидация ответа
        validationJson(runner, HttpStatus.OK, "{\n" +
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
        createDuck(runner, "yellow", 10.0, "wood", "quack", "ACTIVE");

        //Валидация ответа
        validationJson(runner, HttpStatus.OK, "{\n" +
                "  \"id\": \"@ignore@\",\n" +
                "  \"color\": \"yellow\",\n" +
                "  \"height\": 10.0,\n" +
                "  \"material\": \"wood\",\n" +
                "  \"sound\": \"quack\",\n" +
                "  \"wingsState\": \"ACTIVE\"\n" +
                "}");
    }
}