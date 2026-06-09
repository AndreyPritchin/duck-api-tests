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
import payloads.DuckValidationMessagePayload;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Тесты на duck-controller")
@Feature("Обновление характеристик утки")
@Story("Эндпоинт /api/duck/update")
public class DuckUpdateTest extends DuckUpdateClient {

    @Test(description = "Проверка обновления цвета и высоты утки")
    @CitrusTest
    public void testUpdateHeightDuck(@Optional @CitrusResource TestCaseRunner runner) {

        runner.variable("duckId", "1");

        //Удаление тестовой утки из БД
        runner.$(doFinally().actions(context ->
                dataBaseUpdate(runner, "delete from duck where ID = ${duckId}")));

        //Создание утки через БД
        dataBaseUpdate(runner,
                "insert into duck (id, color, height, material, sound, wings_state)\n"+
                "values (${duckId}, 'yellow', 10.0, 'wood', 'quack', 'ACTIVE');");

        //Вызов метода для обновления утки
        updateDuck(runner, "${duckId}", "red", 5, "wood", "quack", "ACTIVE");

        //Валидация ответа resources
        validationJsonResources(runner, HttpStatus.OK, "UpdateDuckTestResources/updateDuck.json");

        //Валидация через БД
        validationDatabase(runner, "${duckId}", "red", "5.0", "wood", "quack", "ACTIVE");
    }

    @Test(description = "Проверка обновления цвета и звука утки")
    @CitrusTest
    public void testUpdateSoundDuck(@Optional @CitrusResource TestCaseRunner runner) {

        runner.variable("duckId", "1");

        //Удаление тестовой утки из БД
        runner.$(doFinally().actions(context ->
                dataBaseUpdate(runner, "delete from duck where ID = ${duckId}")));

        //Создание утки через БД
        dataBaseUpdate(runner,
                "insert into duck (id, color, height, material, sound, wings_state)\n"+
                        "values (${duckId}, 'yellow', 10.0, 'wood', 'quack', 'ACTIVE');");

        //Вызов метода для обновления утки
        updateDuck(runner, "${duckId}", "green", 10.0, "wood", "KuKu", "ACTIVE");

        //Вызов метода для создания параметров утки payload
        DuckValidationMessagePayload expectedDuck = new DuckValidationMessagePayload()
                .message("Duck with id = ${duckId} is updated");

        //Валидация ответа payload
        validationJsonPayload(runner, HttpStatus.OK, expectedDuck);

        //Валидация через БД
        validationDatabase(runner, "${duckId}", "green", "10.0", "wood", "KuKu", "ACTIVE");
    }
}