package autotests.tests;

import autotests.clients.DuckCreateClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.payloads.DuckCreatePayload;
import autotests.payloads.DuckValidationParametersPayload;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Тесты на duck-controller")
@Feature("Создание утки")
@Story("Эндпоинт /api/duck/create")
public class DuckCreateTest extends DuckCreateClient {

    @Test(description = "Проверка создания утки с материалом rubber. Валидация string")
    @CitrusTest
    public void testCreateRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Удаление тестовой утки из БД
        runner.$(doFinally().actions(context ->
                dataBaseUpdate(runner, "delete from duck where ID = ${duckId}")));

        //Вызов метода для создания параметров утки payload
        DuckCreatePayload duckCreatePayload = new DuckCreatePayload()
                .color("yellow")
                .height(10.0)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");
        //Вызов метода для создания утки payload
        createDuckPayload(runner, duckCreatePayload);

        //Вызов метода для создания параметров утки payload
        DuckValidationParametersPayload expectedDuck = new DuckValidationParametersPayload()
                .id("@ignore@")
                .color("yellow")
                .height(10.0)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");
        //Валидация ответа payload и получение id
        validationJsonPayloadExtract(runner, HttpStatus.OK, expectedDuck);

        //Валидация через БД
        validationDatabase(runner, "${duckId}", "yellow", "10.0", "rubber", "quack", "ACTIVE");
    }

    @Test(description = "Проверка создания утки с материалом wood")
    @CitrusTest
    public void testCreateWoodDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Удаление тестовой утки из БД
        runner.$(doFinally().actions(context ->
                dataBaseUpdate(runner, "delete from duck where ID = ${duckId}")));

        //Вызов метода для создания параметров утки
        DuckCreatePayload duckCreatePayload = new DuckCreatePayload()
                .color("yellow")
                .height(10.0)
                .material("wood")
                .sound("quack")
                .wingsState("ACTIVE");
        //Вызов метода для создания утки payload
        createDuckPayload(runner, duckCreatePayload);

        //Валидация ответа resources и получение id
        validationJsonResourcesExtract(runner, HttpStatus.OK, "CreateDuckTestResources/createWoodDuck.json");

        //Валидация через БД
        validationDatabase(runner, "${duckId}", "yellow", "10.0", "wood", "quack", "ACTIVE");
    }
}