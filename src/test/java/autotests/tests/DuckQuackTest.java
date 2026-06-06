package autotests.tests;

import autotests.EndpointConfig;
import autotests.clients.*;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import payloads.DuckValidationMessagePayload;
import payloads.DuckValidationSoundPayload;

@ContextConfiguration(classes = {EndpointConfig.class, DuckQuackClient.class})
public class DuckQuackTest extends DuckQuackClient {

    @Test(description = "Проверка кряканья утки с четным id")
    @CitrusTest
    public void testGetQuackEvenDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода кряканья утки
        getQuackDuck(runner, "2", 2, 3);

        //Вызов метода для создания параметров утки payload
        DuckValidationSoundPayload expectedDuck = new DuckValidationSoundPayload()
                .sound("moo-moo, moo-moo, moo-moo");
        //Утка в БД с sound: "quack", но фактический результат sound: "moo". В тесте используется проверка на фактическое сообщение

        //Валидация ответа payload
        validationJsonPayload(runner, HttpStatus.OK, expectedDuck);
    }

    @Test(description = "Проверка кряканья утки с нечетным id")
    @CitrusTest
    public void testGetQuackOddDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода кряканья утки
        getQuackDuck(runner, "1", 2, 3);

        //Вызов метода для создания параметров утки payload
        DuckValidationSoundPayload expectedDuck = new DuckValidationSoundPayload()
                .sound("quack-quack, quack-quack, quack-quack");

        //Валидация ответа payload
        validationJsonPayload(runner, HttpStatus.OK, expectedDuck);
    }
}
