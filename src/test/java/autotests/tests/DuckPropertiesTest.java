package autotests.tests;

import autotests.EndpointConfig;
import autotests.clients.DuckControllerClient;
import autotests.clients.DuckValidationClient;
import autotests.clients.IdExtractClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class, DuckControllerClient.class, DuckValidationClient.class, IdExtractClient.class})
public class DuckPropertiesTest extends TestNGCitrusSpringSupport {

    @Autowired
    private DuckControllerClient duckControllerClient;
    @Autowired
    private DuckValidationClient duckValidationClient;
    @Autowired
    private IdExtractClient idExtractClient;

    @Test(description = "Проверка характеристик утки с четным id (материал: wood)")
    @CitrusTest
    public void testGetPropertiesEvenDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода характеристик утки
        duckControllerClient.getPropertiesDuck(runner, "2");

        //Валидация ответа
        duckValidationClient.validationJson(runner, HttpStatus.OK, "{\n" +
                "  \"color\": \"yellow\",\n" +
                "  \"height\": \"10.0\",\n" +
                "  \"material\": \"wood\",\n" +
                "  \"sound\": \"quack\",\n" +
                "  \"wingsState\": \"ACTIVE\"\n" +
                "}");
    }
    //По результатам теста: Properties возвращает ошибку на любой четный id

    @Test(description = "Проверка характеристик утки с нечетным id (материал: rubber)")
    @CitrusTest
    public void testGetPropertiesOddDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода характеристик утки
        duckControllerClient.getPropertiesDuck(runner, "1");

        //Валидация ответа
        duckValidationClient.validationJson(runner, HttpStatus.OK, "{\n" +
                "  \"color\": \"yellow\",\n" +
                "  \"height\": \"10.0\",\n" +
                "  \"material\": \"rubber\",\n" +
                "  \"sound\": \"quack\",\n" +
                "  \"wingsState\": \"ACTIVE\"\n" +
                "}");
    }
}