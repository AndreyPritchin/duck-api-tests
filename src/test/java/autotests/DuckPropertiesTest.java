package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckPropertiesTest extends TestNGCitrusSpringSupport {

    //Создание метода для показа характеристик утки
    public void getPropertiesDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client("http://localhost:2222")
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", id));
    }

    @Test(description = "Проверка характеристик утки с четным id (материал: wood)")
    @CitrusTest
    public void testGetPropertiesEvenDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода характеристик утки
        getPropertiesDuck(runner, "2");

        //Валидация ответа
        runner.$(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .body("{\n" +
                        //"  \"id\": \"@ignore@\",\n" +
                        "  \"color\": \"@ignore@\",\n" +
                        "  \"height\": \"@ignore@\",\n" +
                        "  \"material\": \"wood\",\n" +
                        "  \"sound\": \"@ignore@\",\n" +
                        "  \"wingsState\": \"@ignore@\"\n" +
                        "}"));
    }
    //По результатам теста: Properties возвращает ошибку на любой четный id

    @Test(description = "Проверка характеристик утки с нечетным id (материал: rubber)")
    @CitrusTest
    public void testGetPropertiesOddDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода характеристик утки
        getPropertiesDuck(runner, "1");

        //Валидация ответа
        runner.$(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .body("{\n" +
                        //"  \"id\": \"@ignore@\",\n" +
                        "  \"color\": \"@ignore@\",\n" +
                        "  \"height\": \"@ignore@\",\n" +
                        "  \"material\": \"rubber\",\n" +
                        "  \"sound\": \"@ignore@\",\n" +
                        "  \"wingsState\": \"@ignore@\"\n" +
                        "}"));
    }
}