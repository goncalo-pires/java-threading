package dataSharing.syncrhonization;

public class MethodLevelSynchronizationExample {

    public static void main(String[] args) {

        InventoryCounter inventoryCounter = new InventoryCounter();
        IncrementalThread incrementalThread = new IncrementalThread(inventoryCounter);
        DecrementalThread decrementalThread = new DecrementalThread(inventoryCounter);

        incrementalThread.start();
        decrementalThread.start();

        try {
            decrementalThread.join();
            incrementalThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Final number of items: " + inventoryCounter.getItems());

    }

    public static class DecrementalThread extends Thread {

        private final InventoryCounter inventoryCounter;

        public DecrementalThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.decrement();
            }
        }
    }

    public static class IncrementalThread extends Thread {

        private final InventoryCounter inventoryCounter;

        public IncrementalThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.increment();
            }
        }
    }

    public static class InventoryCounter {

        private int items = 0;

        public synchronized void increment() {
            items++;
        }

        public synchronized void decrement() {
            items--;
        }

        public int getItems() {
            return this.items;
        }
    }
}
