import java.util.Random;

public class Client {

    String getGreeting() {
        var randomValue = new Random().nextInt(1, 5);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "" + randomValue;
    }
}
