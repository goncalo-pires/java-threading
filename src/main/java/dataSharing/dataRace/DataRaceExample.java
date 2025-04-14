package dataSharing.dataRace;

import java.util.concurrent.atomic.AtomicInteger;

public class DataRaceExample {

    public static void main(String[] args) {

        SharedClass sharedClass = new SharedClass();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.checkForDataRace();
            }
        });

        thread1.start();
        thread2.start();

    }

    public static class SharedClass {

        private volatile int x = 0;
        private volatile int y = 0;

        public void increment() {
            x++;
            y++;
        }

        public void checkForDataRace() {
            if (y > x) {
                System.out.println("y > x - Data race detected!");
            }
        }
    }

    public static class SharedClass2 {

        private AtomicInteger x = new AtomicInteger(0);
        private AtomicInteger y = new AtomicInteger(0);

        public void increment() {
            x.incrementAndGet();  // Atomically increment x
            y.incrementAndGet();  // Atomically increment y
        }

        public void checkForDataRace() {
            if (y.get() > x.get()) {
                System.out.println("y > x - Data race detected!");
            }
        }
    }
}
