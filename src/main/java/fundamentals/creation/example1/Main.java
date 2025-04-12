package fundamentals.creation.example1;

public class Main {

    public static void main(String[] args) {
        executeGoodThread("Worker thread");
        executeBadThread("Misbehaving thread");
    }

    public static void executeGoodThread(String name) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread name: " + Thread.currentThread().getName());
                System.out.println("Thread priority: " + Thread.currentThread().getPriority());
            }
        });

        thread.setName(name);
        thread.setPriority(Thread.MAX_PRIORITY);
        System.out.println("Before execution of thread " + Thread.currentThread().getName());
        thread.start();
        System.out.println("After execution of thread " + Thread.currentThread().getName());
    }

    public static void executeBadThread(String name) {
        Thread thread = new Thread(() -> {
            throw new RuntimeException("Intentional Exception");
        });

        thread.setName(name);
        thread.setUncaughtExceptionHandler((t, e) -> {
            System.out.println("A critical error happened in thread " + Thread.currentThread().getName() + ". The error is " + e.getMessage());
        });

        thread.start();
    }

}
