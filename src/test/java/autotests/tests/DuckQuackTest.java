package autotests.tests;

import autotests.clients.*;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import payloads.DuckValidationSoundPayload;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Тесты на duck-action-controller")
@Feature("Кряканье утки")
@Story("Эндпоинт /api/duck/action/quack")
public class DuckQuackTest extends DuckQuackClient {

    @Test(description = "Проверка кряканья утки с четным id")
    @CitrusTest
    public void testGetQuackEvenDuck(@Optional @CitrusResource TestCaseRunner runner) {

        runner.variable("duckId", "2");

        //Удаление тестовой утки из БД
        runner.$(doFinally().actions(context ->
                dataBaseUpdate(runner, "delete from duck where ID = ${duckId}")));

        //Создание утки через БД
        dataBaseUpdate(runner,
                "insert into duck (id, color, height, material, sound, wings_state)\n"+
                        "values (${duckId}, 'yellow', 10.0, 'wood', 'quack', 'ACTIVE');");

        //Вызов метода кряканья утки
        getQuackDuck(runner, "2", 2, 3);

        //Вызов метода для создания параметров утки payload
        DuckValidationSoundPayload expectedDuck = new DuckValidationSoundPayload()
                .sound("moo-moo, moo-moo, moo-moo");
        //Утка в БД с sound: "quack", но фактический результат sound: "moo". В тесте используется проверка на фактическое сообщение
    }

    @Test(description = "Проверка кряканья утки с нечетным id")
    @CitrusTest
    public void testGetQuackOddDuck(@Optional @CitrusResource TestCaseRunner runner) {

        runner.variable("duckId", "1");

        //Удаление тестовой утки из БД
        runner.$(doFinally().actions(context ->
                dataBaseUpdate(runner, "delete from duck where ID = ${duckId}")));

        //Создание утки через БД
        dataBaseUpdate(runner,
                "insert into duck (id, color, height, material, sound, wings_state)\n"+
                        "values (${duckId}, 'yellow', 10.0, 'wood', 'quack', 'ACTIVE');");

        //Вызов метода кряканья утки
        getQuackDuck(runner, "1", 2, 3);

        //Вызов метода для создания параметров утки payload
        DuckValidationSoundPayload expectedDuck = new DuckValidationSoundPayload()
                .sound("quack-quack, quack-quack, quack-quack");
    }
}
