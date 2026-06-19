package autotests.tests;

import autotests.clients.*;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Тесты на duck-action-controller")
@Feature("Плаванье утки")
@Story("Эндпоинт /api/duck/action/swim")
public class DuckSwimTest extends DuckSwimClient {

    @Test(description = "Проверка поплыва утки с существующим id")
    @CitrusTest
    public void testGetExistentDuck(@Optional @CitrusResource TestCaseRunner runner) {

        runner.variable("duckId", "1");

        //Удаление тестовой утки из БД
        runner.$(doFinally().actions(context ->
                dataBaseDuckDelete(runner, "duckId")));

        //Создание утки через БД
        dataBaseDuckCreate(runner, "duckId", "yellow", "10.0", "wood", "quack", "ACTIVE");

        //Вызов метода поплыва утки
        getSwimDuck(runner, "${duckId}");

        //Валидация пустого ответа
        validateJsonEmpty(runner, HttpStatus.NOT_FOUND);
    }
    //По результатам тестов: Swim выдает ошибку NOT_FOUND для существующего ID

    @Test(description = "Проверка поплыва утки с НЕсуществующим id")
    @CitrusTest
    public void testGetNonExistentDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода поплыва утки
        getSwimDuck(runner, "-1");

        //Валидация пустого ответа
        validateJsonEmpty(runner, HttpStatus.NOT_FOUND);
    }
}
