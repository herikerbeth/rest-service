package demo.greeting;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GreetingControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    @Test
    void greetingShouldReturnMessageWithName() {
        ResponseEntity<Greeting> response = template.getForEntity(
                "http://localhost:" + port + "/greeting?name=Friend", Greeting.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Greeting greeting = response.getBody();
        assertThat(greeting).isNotNull();
        assertThat(greeting.content()).isEqualTo("Hello, Friend!");
        assertThat(greeting.id()).isGreaterThan(0);
    }

    @Test
    void greetingShouldReturnMessageWithoutName() {
        ResponseEntity<Greeting> response = template.getForEntity(
                "http://localhost:" + port + "/greeting", Greeting.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Greeting greeting = response.getBody();
        assertThat(greeting).isNotNull();
        assertThat(greeting.content()).isEqualTo("Hello, World!");
        assertThat(greeting.id()).isGreaterThan(0);
    }
}
