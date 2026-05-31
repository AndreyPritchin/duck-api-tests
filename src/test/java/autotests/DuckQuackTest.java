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

public class DuckQuackTest extends TestNGCitrusSpringSupport {

    //Создание метода кряканья утки
    public void getQuackDuck(TestCaseRunner runner, String id, int repetitionCount, int soundCount) {
        runner.$(http()
                .client("http://localhost:2222")
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", id)
                .queryParam("repetitionCount", String.valueOf(repetitionCount))
                .queryParam("soundCount", String.valueOf(soundCount)));
    }

    @Test(description = "Проверка кряканья утки с четным id")
    @CitrusTest
    public void testGetQuackEvenDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода характеристик утки
        getQuackDuck(runner, "2", 1, 1);

        //Валидация ответа
        runner.$(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .body("{\n" +
                        "  \"sound\": \"quack\"\n" +
                        "}"));
    }
    //По результатам теста утка с четным id говорит: 'moo' вместо 'quack'

    @Test(description = "Проверка кряканья утки с нечетным id")
    @CitrusTest
    public void testGetQuackOddDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода характеристик утки
        getQuackDuck(runner, "1", 1, 1);

        //Валидация ответа
        runner.$(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .body("{\n" +
                        "  \"sound\": \"quack\"\n" +
                        "}"));
    }
}
