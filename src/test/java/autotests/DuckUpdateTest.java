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

public class DuckUpdateTest extends TestNGCitrusSpringSupport {

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

    //Создание метода для обновления утки
    public void updateDuck(TestCaseRunner runner, String id, String color, double height, String material, String sound, String wingsState) {
        runner.$(http()
                .client("http://localhost:2222")
                .send()
                .put("/api/duck/update")
                .queryParam("id", id)
                .queryParam("color", color)
                .queryParam("height", String.valueOf(height))
                .queryParam("material", material)
                .queryParam("sound", sound)
                .queryParam("wingsState", wingsState));
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

    @Test(description = "Проверка обновления цвета и высоты утки")
    @CitrusTest
    public void testUpdateHeightDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания утки
        createDuck(runner, "yellow", 10.0, "wood", "quack", "ACTIVE");

        String duckId = idExtract(runner);

        //Вызов метода для обновления утки
        updateDuck(runner, "${duckId}", "red", 5, "wood", "quack", "ACTIVE");

        //Валидация ответа
        validationJson(runner, HttpStatus.OK, "{\n" +
                "  \"message\": \"Duck with id = ${duckId} is updated\"\n" +
                "}");
    }

    @Test(description = "Проверка обновления цвета и звука утки")
    @CitrusTest
    public void testUpdateSoundDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания утки
        createDuck(runner, "yellow", 10.0, "wood", "quack", "ACTIVE");

        String duckId = idExtract(runner);

        //Вызов метода для обновления утки
        updateDuck(runner, "${duckId}", "green", 10.0, "wood", "KuKu", "ACTIVE");

        //Валидация ответа
        validationJson(runner, HttpStatus.OK, "{\n" +
                "  \"message\": \"Duck with id = ${duckId} is updated\"\n" +
                "}");
    }
}