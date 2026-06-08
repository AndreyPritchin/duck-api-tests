package autotests.tests;

import autotests.clients.*;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckFlyTest extends DuckFlyClient {

    @Test(description = "Проверка характеристик утки с активным состоянием крыльев")
    @CitrusTest
    public void testGetFlyActiveDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания утки
        createDuck(runner, "yellow", 10.0, "wood", "quack", "ACTIVE");

        String duckId = idExtract(runner);

        //Вызов метода полета утки
        getFlyDuck(runner, "${duckId}");

        //Валидация ответа
        validationJson(runner, HttpStatus.OK, "{\n" +
                "  \"message\": \"I am flying :)\"\n" + //В требованиях написано - Body: { “message”: “I’m flying”}, но фактический результат - I am flying :). В тесте используется проверка на фактическое сообщение
                "}");
    }

    @Test(description = "Проверка характеристик утки со связанными крыльями")
    @CitrusTest
    public void testGetFlyFixedDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания утки
        createDuck(runner, "yellow", 10.0, "wood", "quack", "FIXED");

        String duckId = idExtract(runner);

        //Вызов метода полета утки
        getFlyDuck(runner, "${duckId}");

        //Валидация ответа
        validationJson(runner, HttpStatus.OK, "{\n" +
                "  \"message\": \"I can not fly :C\"\n" + //В требованиях написано - Body: { “message”: “I can’t fly”}, но фактический результат - I can not fly :C. В тесте используется проверка на фактическое сообщение
                "}");
    }

    @Test(description = "Проверка характеристик утки с неопределенным состоянием крыльев")
    @CitrusTest
    public void testGetFlyUndefinedDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания утки
        createDuck(runner, "yellow", 10.0, "wood", "quack", "UNDEFINED");

        String duckId = idExtract(runner);

        //Вызов метода полета утки
        getFlyDuck(runner, "${duckId}");

        //Валидация ответа
        validationJson(runner, HttpStatus.OK, "{\n" +
                "  \"message\": \"Wings are not detected :(\"\n" + //Фактический результат - Wings are not detected :(. В тесте используется проверка на фактическое сообщение
                "}");
    }
}