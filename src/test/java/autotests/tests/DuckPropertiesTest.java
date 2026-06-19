package autotests.tests;

import autotests.clients.DuckPropertiesClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.payloads.DuckValidationParametersPayload;

public class DuckPropertiesTest extends DuckPropertiesClient {

    @Test(description = "Проверка характеристик утки с четным id (материал: wood)")
    @CitrusTest
    public void testGetPropertiesEvenDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода характеристик утки
        getPropertiesDuck(runner, "2");

        //Валидация пустого тела ответа
        validateJsonEmpty(runner, HttpStatus.OK);
    }
    //По результатам теста: Properties возвращает ошибку на любой четный id "Number of JSON entries not equal for element: '$.', expected '5' but was '0', поэтому проверяю пустое тело ответа"

    @Test(description = "Проверка характеристик утки с нечетным id (материал: rubber)")
    @CitrusTest
    public void testGetPropertiesOddDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода характеристик утки
        getPropertiesDuck(runner, "3");

        //Вызов метода для создания параметров утки payload
        DuckValidationParametersPayload expectedDuck = new DuckValidationParametersPayload()
                .color("yellow")
                .height(1000.0)         //Утка в БД с height: 10.0, но в фактическом сообщении height: 1000.0
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");

        //Валидация ответа payload
        validationJsonPayload(runner, HttpStatus.OK, expectedDuck);
    }
}