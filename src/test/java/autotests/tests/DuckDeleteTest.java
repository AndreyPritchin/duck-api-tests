package autotests.tests;

import autotests.EndpointConfig;
import autotests.clients.DuckDeleteClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@ContextConfiguration(classes = {EndpointConfig.class, DuckDeleteClient.class})
public class DuckDeleteTest extends DuckDeleteClient {

    @Test(description = "Проверка удаления утки")
    @CitrusTest
    public void testDeleteDuck(@Optional @CitrusResource TestCaseRunner runner) {

        //Вызов метода для создания утки
        createDuck(runner, "yellow", 10.0, "rubber", "quack", "ACTIVE");

        String duckId = idExtract(runner);

        deleteDuck(runner, "${duckId}");

        validationJson(runner, HttpStatus.OK, "{\n" +
                "  \"message\": \"Duck is deleted\"\n" +
                "}");
    }
}
