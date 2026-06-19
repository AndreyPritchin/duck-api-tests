package autotests.tests;

import autotests.clients.*;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.payloads.DuckCreatePayload;

public class DuckSwimTest extends DuckSwimClient {

    @Test(description = "Проверка поплыва утки с существующим id")
    @CitrusTest
    public void testGetExistentDuck(@Optional @CitrusResource TestCaseRunner runner) {

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

        //Вызов метода поплыва утки
        getSwimDuck(runner, duckId);

        //Валидация пустого тела ответа
        validateJsonEmpty(runner, HttpStatus.NOT_FOUND);
    }
    //По результатам тестов: Swim выдает ошибку NOT_FOUND для существующего ID

    @Test(description = "Проверка поплыва утки с НЕсуществующим id")
    @CitrusTest
    public void testGetNonExistentDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания параметров утки payload
        DuckCreatePayload duckCreatePayload = new DuckCreatePayload()
                .color("yellow")
                .height(10.0)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");
        //Вызов метода для создания утки payload
        createDuckPayload(runner, duckCreatePayload);

        //Вызов метода поплыва утки
        getSwimDuck(runner, "-1");

        //Валидация пустого тела ответа
        validateJsonEmpty(runner, HttpStatus.NOT_FOUND);
    }
}
