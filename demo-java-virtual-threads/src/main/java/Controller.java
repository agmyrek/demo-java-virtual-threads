public class Controller {

    public void greetings() throws InterruptedException {
        System.out.println("Demo Virtual Threads - Java 25");
        var client = new Client();
        var threadBuilder = Thread.ofVirtual();

        var t1 = threadBuilder.start(() -> {
            var result = client.getGreeting();
            System.out.println("Task of virtual thread1 returned: " + result);
        });

        var t2 = threadBuilder.start(() -> {
            var result = client.getGreeting();
            System.out.println("Task of virtual thread2 returned: " + result);
        });


        // Wait for the threads to finish so the main thread (greetings method) doesn't exit too early
        t1.join();
        t2.join();

    }
}
