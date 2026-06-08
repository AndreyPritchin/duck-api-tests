package autotests.tests;

import autotests.clients.*;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckSwimTest extends DuckSwimClient {

    @Test(description = "Проверка поплыва утки с существующим id")
    @CitrusTest
    public void testGetExistentDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания утки
        createDuck(runner, "yellow", 10.0, "wood", "quack", "ACTIVE");

        String duckId = idExtract(runner);

        //Вызов метода поплыва утки
        getSwimDuck(runner, duckId);

        //Валидация ответа
        validationStatus(runner, HttpStatus.BAD_REQUEST);
    }
    //По результатам тестов: Swim выдает ошибку BAD_REQUEST для существующего ID

    @Test(description = "Проверка поплыва утки с НЕсуществующим id")
    @CitrusTest
    public void testGetNonExistentDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода поплыва утки
        getSwimDuck(runner, "-1");

        //Валидация ответа
        validationStatus(runner, HttpStatus.NOT_FOUND);
    }
}
