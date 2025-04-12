package fundamentals.creation.caseStudy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Vault Cracking Simulation - Multithreading Case Study
 * <p>
 * This example demonstrates a simple multithreaded scenario where two hacker threads
 * attempt to brute-force a vault's password while a police thread counts down.
 * <p>
 * Key concepts demonstrated:
 * - Creating threads by extending the Thread class
 * - Using thread priorities and custom names
 * - Simulating concurrent competition between threads
 * - Terminating the application with System.exit(0) from any thread
 * <p>
 * The program ends when either:
 * - A hacker thread successfully guesses the password
 * - The police thread finishes its countdown
 */

public class VaultCrackingSimulation {

    public static final int MAX_PASSWORD = 5555;

    public static void main(String[] args) {
        Random random = new Random();

        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));

        List<Thread> threadList = new ArrayList<>();

        threadList.add(new AscendingHackerThread(vault));
        threadList.add(new DescendingHackerThread(vault));
        threadList.add(new PoliceThread());

        threadList.forEach(Thread::start);

    }

    private static class Vault {
        private final int password;
        public Vault(int password) {
            this.password = password;
        }
        public boolean isCorrectPassword(int guess) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return this.password == guess;
        }
    }

    private abstract static class HackerThread extends Thread {
        protected Vault vault;
        public HackerThread(Vault vault) {
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(Thread.MAX_PRIORITY);
        }

        @Override
        public void start() {
            System.out.println("Starting thread " + this.getName());
            super.start();
        }
    }

    private static class AscendingHackerThread extends HackerThread {

        public AscendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess=0; guess<=MAX_PASSWORD; guess++) {
                if (vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " found correct password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class DescendingHackerThread extends HackerThread {

        public DescendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess=MAX_PASSWORD; guess>=0; guess--) {
                if (vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " found correct password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class PoliceThread extends Thread {

        @Override
        public void run() {
            for (int i=10; i>0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Seconds to arrive: " + i);
            }
            System.out.println("Game over for hackers!");
            System.exit(0);
        }
    }

}
