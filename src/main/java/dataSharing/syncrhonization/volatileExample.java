package dataSharing.syncrhonization;

import java.util.Random;

public class volatileExample {

    public static void main(String[] args) {

        Metrics metrics = new Metrics();
        BusinessLogic businessLogic1 = new BusinessLogic(metrics);
        BusinessLogic businessLogic2 = new BusinessLogic(metrics);
        MetricsPrinter metricsPrinter = new MetricsPrinter(metrics);

        businessLogic1.start();
        businessLogic2.start();
        metricsPrinter.start();
    }

    private static class MetricsPrinter extends Thread {

        private final Metrics metrics;

        public MetricsPrinter(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("Thread " + Thread.currentThread().getName() + " was interrupted");
                }
                double average = metrics.getAverage();
                System.out.println("Current average is " + average);
            }
        }
    }


    private static class BusinessLogic extends Thread {

        private final Metrics metrics;
        private final Random random = new Random();

        public BusinessLogic(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            while (true) {
                long start = System.currentTimeMillis();

                try {
                    Thread.sleep(random.nextInt(10));
                } catch (InterruptedException e) {
                    System.out.println("Thread " + Thread.currentThread().getName() + " was interrupted");
                }

                long end = System.currentTimeMillis();

                metrics.addSample(end - start);
            }
        }

    }

    private static class Metrics {

        private long counter = 0;
        private volatile double average = 0.0;

        public synchronized void addSample(long sample) {
            double sum = counter * average + sample;
            counter++;
            average = sum / counter;
        }

        public double getAverage() {
            return this.average;
        }
    }

}
