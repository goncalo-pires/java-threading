package src.fundamentals.creation.example2;

public class Main {

    public static void main(String[] args) {
        Thread thread = new CustomThread();
        thread.start();
    }

    private static class CustomThread extends Thread {
        @Override
        public void run() {
            System.out.println("Hello from " + this.getName());
        }
    }

}
