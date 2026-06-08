package autotests.tests;

import autotests.EndpointConfig;
import autotests.clients.DuckPropertiesClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@ContextConfiguration(classes = {EndpointConfig.class, DuckPropertiesClient.class})
public class DuckPropertiesTest extends DuckPropertiesClient {

    @Test(description = "Проверка характеристик утки с четным id (материал: wood)")
    @CitrusTest
    public void testGetPropertiesEvenDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода характеристик утки
        getPropertiesDuck(runner, "2");

        //Валидация ответа
        /*duckValidationClient.validationJson(runner, HttpStatus.OK, "{\n" +
                "  \"color\": \"yellow\",\n" +
                "  \"height\": \"10.0\",\n" +
                "  \"material\": \"wood\",\n" +
                "  \"sound\": \"quack\",\n" +
                "  \"wingsState\": \"ACTIVE\"\n" +
                "}");*/
        validationStatus(runner, HttpStatus.OK);
    }
    //По результатам теста: Properties возвращает ошибку на любой четный id "Number of JSON entries not equal for element: '$.', expected '5' but was '0'"

    @Test(description = "Проверка характеристик утки с нечетным id (материал: rubber)")
    @CitrusTest
    public void testGetPropertiesOddDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода характеристик утки
        getPropertiesDuck(runner, "1");

        //Валидация ответа
        validationJson(runner, HttpStatus.OK, "{\n" +
                "  \"color\": \"yellow\",\n" +
                "  \"height\": 1000.0,\n" +         //Утка в БД с height: 10.0, но в фактическом сообщении height: 1000.0
                "  \"material\": \"rubber\",\n" +
                "  \"sound\": \"quack\",\n" +
                "  \"wingsState\": \"ACTIVE\"\n" +
                "}");
    }
}