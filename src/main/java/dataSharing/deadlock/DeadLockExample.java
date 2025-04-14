package dataSharing.deadlock;

import java.util.Random;

public class DeadLockExample {

    public static void main(String[] args) {

        Intersection intersection = new Intersection();
        TrainA trainA = new TrainA(intersection);
        TrainB trainB = new TrainB(intersection);

        trainA.start();
        trainB.start();

    }

    public static class TrainB extends Thread {

        private Intersection intersection;
        private Random random = new Random();

        public TrainB(Intersection intersection) {
            this.intersection = intersection;
        }

        @Override
        public void run() {
            while (true) {
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                    System.out.println("Thread " + Thread.currentThread().getName() + " was interrupted");
                }
                intersection.takeRoadB();
            }
        }
    }

    public static class TrainA extends Thread {

        private Intersection intersection;
        private Random random = new Random();

        public TrainA(Intersection intersection) {
            this.intersection = intersection;
        }

        @Override
        public void run() {
            while (true) {
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                    System.out.println("Thread " + Thread.currentThread().getName() + " was interrupted");
                }
                intersection.takeRoadA();
            }
        }
    }

    public static class Intersection {
        private Object roadA = new Object();
        private Object roadB = new Object();

        public void takeRoadA() {
            synchronized (this.roadA) {
                System.out.println("Lock on roadA has been acquired by " + Thread.currentThread().getName());
                synchronized (this.roadB) {
                    System.out.println("Train is passing through road A");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        System.out.println("Thread " + Thread.currentThread().getName() + " was interrupted");
                    }
                }
            }
        }

        public void takeRoadB() {
            synchronized (this.roadA) {
                System.out.println("Lock on roadB has been acquired by " + Thread.currentThread().getName());
                synchronized (this.roadB) {
                    System.out.println("Train is passing through road B");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        System.out.println("Thread " + Thread.currentThread().getName() + " was interrupted");
                    }
                }
            }
        }

        // Wrong implementation
        public void takeRoadB2() {
            synchronized (this.roadB) {
                System.out.println("Lock on roadB has been acquired by " + Thread.currentThread().getName());
                synchronized (this.roadA) {
                    System.out.println("Train is passing through road B");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        System.out.println("Thread " + Thread.currentThread().getName() + " was interrupted");
                    }
                }
            }
        }

    }
}
