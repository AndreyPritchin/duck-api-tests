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
import autotests.payloads.DuckValidationMessagePayload;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Тесты на duck-action-controller")
@Feature("Полет утки")
@Story("Эндпоинт /api/duck/action/fly")
public class DuckFlyTest extends DuckFlyClient {

    @Test(description = "Проверка характеристик утки с активным состоянием крыльев")
    @CitrusTest
    public void testGetFlyActiveDuck(@Optional @CitrusResource TestCaseRunner runner) {

        runner.variable("duckId", "1");

        //Удаление тестовой утки из БД
        runner.$(doFinally().actions(context ->
                dataBaseUpdate(runner, "delete from duck where ID = ${duckId}")));

        //Создание утки через БД
        dataBaseUpdate(runner,
                "insert into duck (id, color, height, material, sound, wings_state)\n"+
                        "values (${duckId}, 'yellow', 10.0, 'wood', 'quack', 'ACTIVE');");

        //Вызов метода полета утки
        getFlyDuck(runner, "${duckId}");

        //Вызов метода для создания параметров утки payload
        DuckValidationMessagePayload expectedDuck = new DuckValidationMessagePayload()
                .message("I am flying :)");

        //Валидация ответа payload
        validationJsonPayload(runner, HttpStatus.OK, expectedDuck);
    }

    @Test(description = "Проверка характеристик утки со связанными крыльями")
    @CitrusTest
    public void testGetFlyFixedDuck(@Optional @CitrusResource TestCaseRunner runner) {

        runner.variable("duckId", "1");

        //Удаление тестовой утки из БД
        runner.$(doFinally().actions(context ->
                dataBaseUpdate(runner, "delete from duck where ID = ${duckId}")));

        //Создание утки через БД
        dataBaseUpdate(runner,
                "insert into duck (id, color, height, material, sound, wings_state)\n"+
                        "values (${duckId}, 'yellow', 10.0, 'wood', 'quack', 'FIXED');");

        //Вызов метода полета утки
        getFlyDuck(runner, "${duckId}");

        //Вызов метода для создания параметров утки payload
        DuckValidationMessagePayload expectedDuck = new DuckValidationMessagePayload()
                .message("I can not fly :C");

        //Валидация ответа payload
        validationJsonPayload(runner, HttpStatus.OK, expectedDuck);
    }

    @Test(description = "Проверка характеристик утки с неопределенным состоянием крыльев")
    @CitrusTest
    public void testGetFlyUndefinedDuck(@Optional @CitrusResource TestCaseRunner runner) {

        runner.variable("duckId", "1");

        //Удаление тестовой утки из БД
        runner.$(doFinally().actions(context ->
                dataBaseUpdate(runner, "delete from duck where ID = ${duckId}")));

        //Создание утки через БД
        dataBaseUpdate(runner,
                "insert into duck (id, color, height, material, sound, wings_state)\n"+
                        "values (${duckId}, 'yellow', 10.0, 'wood', 'quack', 'UNDEFINED');");

        //Вызов метода полета утки
        getFlyDuck(runner, "${duckId}");

        //Вызов метода для создания параметров утки payload
        DuckValidationMessagePayload expectedDuck = new DuckValidationMessagePayload()
                .message("Wings are not detected :(");

        //Валидация ответа payload
        validationJsonPayload(runner, HttpStatus.OK, expectedDuck);
    }
}