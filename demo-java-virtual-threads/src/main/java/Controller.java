import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Controller {

    private final Client client;
    List<Thread> virtualThreads = new ArrayList<>();
    List<Future> futures = new ArrayList<>();
    public static final int VIRTUAL_THREADS_COUNT = 10;

    public Controller() {
        client = new Client();
    }

    // Simple thread building with Thread.ofVirtual()
    public void greetings1() {

        System.out.println("Demo Virtual Threads - Java 25");

        // Thread builder
        var threadBuilder = Thread.ofVirtual();

        for (int i = 0; i < VIRTUAL_THREADS_COUNT; i++) {
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

    // Virtual threads with ExecutorService so that we can get return values using futures
    public void greetings2() {
        try (var executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < VIRTUAL_THREADS_COUNT; i++) {
                var future = executorService.submit(client::getGreeting);
                futures.add(future);
            }
        }
        futures.forEach(future -> {
            try {
                // mithilfe von get() kann ich auf das Ergebnis des Futures zugreifen
                // der Aufruf von get() blockiert den Hauptthread, bis das Ergebnis vorliegt
                System.out.println("Future returned: " + future.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
