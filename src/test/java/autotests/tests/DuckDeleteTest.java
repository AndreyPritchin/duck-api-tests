package autotests.tests;

import autotests.clients.*;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Тесты на duck-controller")
@Feature("Удаление утки")
@Story("Эндпоинт /api/duck/delete")
public class DuckDeleteTest extends DuckDeleteClient {

    @Test(description = "Проверка удаления утки")
    @CitrusTest
    public void testDeleteDuck(@Optional @CitrusResource TestCaseRunner runner) {

        runner.variable("duckId", "1");

        //Создание утки через БД
        dataBaseUpdate(runner,
                "insert into duck (id, color, height, material, sound, wings_state)\n"+
                        "values (${duckId}, 'yellow', 10.0, 'wood', 'quack', 'ACTIVE');");

        //Вызов метода удаления утки
        deleteDuck(runner, "${duckId}");

        //Валидация ответа resources
        validationJsonResources(runner, HttpStatus.OK, "DeleteDuckTestResources/deleteDuck.json");

        //Проверка удаления через БД
        validateDeletedDatabase(runner, "${duckId}");
    }
}
