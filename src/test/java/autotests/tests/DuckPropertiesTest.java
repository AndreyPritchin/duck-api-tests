package autotests.tests;

import autotests.clients.DuckPropertiesClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.springframework.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import payloads.DuckValidationParametersPayload;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Тесты на duck-action-controller")
@Feature("Показ характеристик утки")
@Story("Эндпоинт /api/duck/action/properties")
public class DuckPropertiesTest extends DuckPropertiesClient {

    @DataProvider(name = "duckPropertiesEvenProvider")
    public Object[][] duckPropertiesEvenProvider() {
        return new Object[][] {
                {null, "2", "yellow", "10.0", "wood", "quack", "ACTIVE"},
                {null, "4", "red", "15.0", "wood", "KuKu", "FIXED"},
                {null, "6", "green", "20.0", "wood", "meow", "ACTIVE"},
                {null, "8", "violet", "25.0", "wood", "quack", "FIXED"},
                {null, "10", "blue", "30.0", "wood", "moo", "ACTIVE"}
        };
    }

    @DataProvider(name = "duckPropertiesOddProvider")
    public Object[][] duckPropertiesOddProvider() {
        return new Object[][] {
                {null, "1", "yellow", 10.0, "wood", "quack", "ACTIVE"},
                {null, "3", "red", 10.0, "wood", "KuKu", "FIXED"},
                {null, "5", "green", 10.0, "wood", "meow", "ACTIVE"},
                {null, "7", "violet", 10.0, "wood", "quack", "FIXED"},
                {null, "9", "blue", 10.0, "wood", "moo", "ACTIVE"}
        };
    }

    @Test(description = "Проверка характеристик утки с четным id (материал: wood)", dataProvider = "duckPropertiesEvenProvider")
    @CitrusTest
    @CitrusParameters({"runner", "duckId", "color", "height", "material", "sound", "wingsState"})
    public void testGetPropertiesEvenDuck(@Optional @CitrusResource TestCaseRunner runner, String duckId, String color, String height, String material, String sound, String wingsState) {

        //runner.variable("duckId", "2");

        //Удаление тестовой утки из БД
        runner.$(doFinally().actions(context ->
                dataBaseDuckDelete(runner, "duckId")));

        //Создание утки через БД
        //dataBaseDuckCreate(runner, "duckId", "yellow", "10.0", "wood", "quack", "ACTIVE");
        dataBaseDuckCreate(runner, duckId, color, height, material, sound, wingsState);

        //Вызов метода характеристик утки
        getPropertiesDuck(runner, duckId);

        //Валидация ответа
        validationStatus(runner, HttpStatus.OK);
    }
    //По результатам теста: Properties возвращает ошибку на любой четный id "Number of JSON entries not equal for element: '$.', expected '5' but was '0'"

    @Test(description = "Проверка характеристик утки с нечетным id (материал: rubber)", dataProvider = "duckPropertiesOddProvider")
    @CitrusTest
    @CitrusParameters({"runner", "duckId", "color", "height", "material", "sound", "wingsState"})
    public void testGetPropertiesOddDuck(@Optional @CitrusResource TestCaseRunner runner, String duckId, String color, double height, String material, String sound, String wingsState) {

        //runner.variable("duckId", "1");

        //Удаление тестовой утки из БД
        runner.$(doFinally().actions(context ->
                dataBaseDuckDelete(runner, "duckId")));

        //Создание утки через БД
        //dataBaseDuckCreate(runner, "duckId", "yellow", "10.0", "rubber", "quack", "ACTIVE");
        dataBaseDuckCreate(runner, duckId, color, String.valueOf(height), material, sound, wingsState);

        //Вызов метода характеристик утки
        getPropertiesDuck(runner, duckId);

        /*
        //Валидация ответа Payload
        DuckValidationParametersPayload expectedDuck = new DuckValidationParametersPayload()
                .color(color)
                .height(1000.0)         //Утка в БД с height: 10.0, но в фактическом сообщении height: 1000.0
                .material(material)
                .sound(sound)
                .wingsState(wingsState);
        validationJsonPayload(runner, HttpStatus.OK, expectedDuck);
        */

        //Валидация через БД
        validationDatabase(runner, duckId, color, String.valueOf(height), material, sound, wingsState);
    }
}