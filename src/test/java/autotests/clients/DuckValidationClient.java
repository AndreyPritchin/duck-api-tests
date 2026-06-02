package autotests.clients;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import com.consol.citrus.http.client.HttpClient;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@Component
public class DuckValidationClient extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient duckService;

    //Метод валидации статус-кода и json-сообщения
    public void validationJson(TestCaseRunner runner, HttpStatus statusCode, String jsonMessage) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(statusCode)
                .message()
                .type(MessageType.JSON)
                .body(jsonMessage));
    }

    //Метод валидации только статус-кода
    public void validationStatus(TestCaseRunner runner, HttpStatus statusCode) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(statusCode)
                .message());
    }
}