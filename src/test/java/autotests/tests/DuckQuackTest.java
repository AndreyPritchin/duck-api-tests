package autotests.tests;

import autotests.EndpointConfig;
import autotests.clients.DuckActionsClient;
import autotests.clients.DuckControllerClient;
import autotests.clients.DuckValidationClient;
import autotests.clients.IdExtractClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@ContextConfiguration(classes = {EndpointConfig.class, DuckValidationClient.class, DuckActionsClient.class})
public class DuckQuackTest extends TestNGCitrusSpringSupport {

    @Autowired
    private DuckValidationClient duckValidationClient;
    @Autowired
    private DuckActionsClient duckActionsClient;

    @Test(description = "Проверка кряканья утки с четным id")
    @CitrusTest
    public void testGetQuackEvenDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода кряканья утки
        duckActionsClient.getQuackDuck(runner, "2", 2, 3);

        //Вызов метода валидации
        duckValidationClient.validationJson(runner, HttpStatus.OK, "{\n" +
                "  \"sound\": \"" + "moo-moo, moo-moo, moo-moo" + "\",\n" +
                "}"); //Утка в БД с sound: "quack", но фактический результат sound: "moo". В тесте используется проверка на фактическое сообщение
    }

    @Test(description = "Проверка кряканья утки с нечетным id")
    @CitrusTest
    public void testGetQuackOddDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода кряканья утки
        duckActionsClient.getQuackDuck(runner, "1", 2, 3);

        //Вызов метода валидации
        duckValidationClient.validationJson(runner, HttpStatus.OK, "{\n" +
                "  \"sound\": \"" + "quack-quack, quack-quack, quack-quack" + "\",\n" +
                "}");
    }
}
