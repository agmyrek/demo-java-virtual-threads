import java.util.ArrayList;
import java.util.List;

public class Controller {

    private final Client client;
    List<Thread> virtualThreads = new ArrayList<>();
    public static final int VIRTUAL_THREADS_COUNT = 100;

    public Controller() {
        client = new Client();
    }

    // Simple thread building with Thread.ofVirtual()
    public void greetings1() {

        System.out.println("Demo Virtual Threads - Java 25");

        // Thread builder
        var threadBuilder = Thread.ofVirtual();

        for(int i = 0; i < VIRTUAL_THREADS_COUNT; i++) {
            var thread = threadBuilder.start(
                    () -> System.out.println("Started new virtual thread. Returned: " + client.getGreeting())
            );
            // add thread to list to keep track of it
            virtualThreads.add(thread);
        }


        virtualThreads.forEach(thread -> {
            try {
                // Wait for the threads to finish so the main thread (greetings method) doesn't exit too early
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(thread.getName(), e);
            }
        });

        // Hinweis
        // wie kann ich die Ergebnisse aus den Threads zurückbekommen?
        // - hier garnicht, weil der Thread builder NICHT die Rückgabe von Werten unterstützt
    }
}
