package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import com.consol.citrus.http.client.HttpClient;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@Component
//@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckValidationClient extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient duckService;

    //@Bean("validationJson")
    public void validationJson(TestCaseRunner runner, HttpStatus statusCode, String jsonMessage) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(statusCode)
                .message()
                .type(MessageType.JSON)
                .body(jsonMessage));
    }

    //@Bean("validationStatus")
    public void validationStatus(TestCaseRunner runner, HttpStatus statusCode) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(statusCode)
                .message());

    }
}