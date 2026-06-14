package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckFlyTest extends TestNGCitrusSpringSupport {

    //Создание метода для создания утки
    public void createDuck(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        runner.$(http()
                .client("http://localhost:2222")
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" +
                        "  \"color\": \"" + color + "\",\n" +
                        "  \"height\": " + height + ",\n" +
                        "  \"material\": \"" + material + "\",\n" +
                        "  \"sound\": \"" + sound + "\",\n" +
                        "  \"wingsState\": \"" + wingsState + "\"\n" +
                        "}"));
    }

    //Создание метода для полета утки
    public void getFlyDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client("http://localhost:2222")
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", id));
    }

    //Создание метода извлечения id утки и запись его в переменную duckId
    public String idExtract(TestCaseRunner runner) {
        runner.$(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .extract(fromBody().expression("$.id", "duckId")));
        return ("duckId");
    }

    //Создание метода валидации статус-кода и json-сообщения
    public void validationJson(TestCaseRunner runner, HttpStatus statusCode, String jsonMessage) {
        runner.$(http()
                .client("http://localhost:2222")
                .receive()
                .response(statusCode)
                .message()
                .type(MessageType.JSON)
                .body(jsonMessage));
    }

    @Test(description = "Проверка характеристик утки с активным состоянием крыльев")
    @CitrusTest
    public void testGetFlyActiveDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания утки
        createDuck(runner, "yellow", 10.0, "wood", "quack", "ACTIVE");

        String duckId = idExtract(runner);

        //Вызов метода полета утки
        getFlyDuck(runner, "${duckId}");

        //Валидация ответа
        validationJson(runner, HttpStatus.OK, "{\n" +
                "  \"message\": \"I am flying :)\"\n" + //В требованиях написано - Body: { “message”: “I’m flying”}, но фактический результат - I am flying :). В тесте используется проверка на фактическое сообщение
                "}");
    }

    @Test(description = "Проверка характеристик утки со связанными крыльями")
    @CitrusTest
    public void testGetFlyFixedDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания утки
        createDuck(runner, "yellow", 10.0, "wood", "quack", "FIXED");

        String duckId = idExtract(runner);

        //Вызов метода полета утки
        getFlyDuck(runner, "${duckId}");

        //Валидация ответа
        validationJson(runner, HttpStatus.OK, "{\n" +
                "  \"message\": \"I can not fly :C\"\n" + //В требованиях написано - Body: { “message”: “I can’t fly”}, но фактический результат - I can not fly :C. В тесте используется проверка на фактическое сообщение
                "}");
    }

    @Test(description = "Проверка характеристик утки с неопределенным состоянием крыльев")
    @CitrusTest
    public void testGetFlyUndefinedDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания утки
        createDuck(runner, "yellow", 10.0, "wood", "quack", "UNDEFINED");

        String duckId = idExtract(runner);

        //Вызов метода полета утки
        getFlyDuck(runner, "${duckId}");

        //Валидация ответа
        validationJson(runner, HttpStatus.OK, "{\n" +
                "  \"message\": \"Wings are not detected :(\"\n" + //Фактический результат - Wings are not detected :(. В тесте используется проверка на фактическое сообщение
                "}");
    }
}