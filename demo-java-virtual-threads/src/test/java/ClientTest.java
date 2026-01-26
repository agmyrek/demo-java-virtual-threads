import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClientTest {

    @Test
    void testGreetMultipleTimesReturnsString() {
        // given
        var client = new Client();
        // when
        var result = client.getGreeting();

        assertNotNull(result);
    }
}
