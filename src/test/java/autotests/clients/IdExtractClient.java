package autotests.clients;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import com.consol.citrus.http.client.HttpClient;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@Component
public class IdExtractClient extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient duckService;

    //Метод извлечения id утки и запись его в переменную duckId
    public String idExtract(TestCaseRunner runner) {
             runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .extract(fromBody().expression("$.id", "duckId")));
             return("duckId");
    }
}
