package autotests.tests;

import autotests.clients.DuckPropertiesClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import payloads.DuckValidationParametersPayload;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

public class DuckPropertiesTest extends DuckPropertiesClient {

    @Test(description = "Проверка характеристик утки с четным id (материал: wood)")
    @CitrusTest
    public void testGetPropertiesEvenDuck(@Optional @CitrusResource TestCaseRunner runner) {

        runner.variable("duckId", "2");

        //Удаление тестовой утки из БД
        runner.$(doFinally().actions(context ->
                dataBaseUpdate(runner, "delete from duck where ID = ${duckId}")));

        //Создание утки через БД
        dataBaseUpdate(runner,
                "insert into duck (id, color, height, material, sound, wings_state)\n"+
                        "values (${duckId}, 'yellow', 10.0, 'wood', 'quack', 'ACTIVE');");

        //Вызов метода характеристик утки
        getPropertiesDuck(runner, "${duckId}");

        validationStatus(runner, HttpStatus.OK);

        //Валидация через БД
        validationDatabase(runner, "${duckId}", "yellow", "10.0", "wood", "quack", "ACTIVE");
    }
    //По результатам теста: Properties возвращает ошибку на любой четный id "Number of JSON entries not equal for element: '$.', expected '5' but was '0'"

    @Test(description = "Проверка характеристик утки с нечетным id (материал: rubber)")
    @CitrusTest
    public void testGetPropertiesOddDuck(@Optional @CitrusResource TestCaseRunner runner) {

        runner.variable("duckId", "1");

        //Удаление тестовой утки из БД
        runner.$(doFinally().actions(context ->
                dataBaseUpdate(runner, "delete from duck where ID = ${duckId}")));

        //Создание утки через БД
        dataBaseUpdate(runner,
                "insert into duck (id, color, height, material, sound, wings_state)\n"+
                        "values (${duckId}, 'yellow', 10.0, 'rubber', 'quack', 'ACTIVE');");

        //Вызов метода характеристик утки
        getPropertiesDuck(runner, "${duckId}");

        //Вызов метода для создания параметров утки payload
        DuckValidationParametersPayload expectedDuck = new DuckValidationParametersPayload()
                .color("yellow")
                .height(1000.0)         //Утка в БД с height: 10.0, но в фактическом сообщении height: 1000.0
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");
        //Валидация ответа payload
        validationJsonPayload(runner, HttpStatus.OK, expectedDuck);

        //Валидация через БД
        validationDatabase(runner, "${duckId}", "yellow", "10.0", "rubber", "quack", "ACTIVE");
    }
}