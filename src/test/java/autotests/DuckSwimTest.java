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

public class DuckSwimTest extends TestNGCitrusSpringSupport {

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

    //Создание метода для поплыва утки
    public void getSwimDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client("http://localhost:2222")
                .send()
                .get("/api/duck/action/swim")//Путь в требованиях: /api/duck/swim ,в swagger написано: /api/duck/action/swim
                .queryParam("id", id));
    }

    @Test(description = "Проверка поплыва утки с существующим id")
    @CitrusTest
    public void testGetExistentDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания утки
        createDuck(runner, "yellow", 10.0, "wood", "quack", "ACTIVE");

        //Валидация JSON-ответа
        runner.$(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .body("{\n" +
                        "  \"id\": \"@ignore@\",\n" +
                        "  \"color\": \"yellow\",\n" +
                        "  \"height\": 10.0,\n" +
                        "  \"material\": \"wood\",\n" +
                        "  \"sound\": \"quack\",\n" +
                        "  \"wingsState\": \"ACTIVE\"\n" +
                        "}")
                //Записываем id в переменную duckId
                .extract(fromBody().expression("$.id", "duckId")));

        //Вызов метода полета утки
        getSwimDuck(runner, "${duckId}");

        //Валидация ответа
        runner.$(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .body("{\n" +
                        "  \"message\": \"I'm swimming\"\n" +
                        "}"));
    }

    @Test(description = "Проверка поплыва утки с НЕсуществующим id")
    @CitrusTest
    public void testGetNonExistentDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания утки
        createDuck(runner, "yellow", 10.0, "wood", "quack", "ACTIVE");

        //Валидация JSON-ответа
        runner.$(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .body("{\n" +
                        "  \"id\": \"@ignore@\",\n" +
                        "  \"color\": \"yellow\",\n" +
                        "  \"height\": 10.0,\n" +
                        "  \"material\": \"wood\",\n" +
                        "  \"sound\": \"quack\",\n" +
                        "  \"wingsState\": \"ACTIVE\"\n" +
                        "}")
                //Записываем id в переменную duckId
                .extract(fromBody().expression("$.id", "duckId")));

        int duckIdInt = Integer.parseInt("duckId");
        duckIdInt++;
        String duckId = String.valueOf(duckIdInt);

        //Вызов метода поплыва утки
        getSwimDuck(runner, "${duckId}");

        //Валидация ответа
        runner.$(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.BAD_REQUEST));
    }
    //По результатам тестов: Swim выдает ошибку NOT_FOUND
}
