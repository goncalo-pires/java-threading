package fundamentals.cordination.example3;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Long> inputNumbers = Arrays.asList(0L, 1244L, 2312324L, 14145L, 124512L);

        List<FactorialThread> listThreads = new ArrayList<>();

        inputNumbers.forEach(number -> listThreads.add(new FactorialThread(number)));

        listThreads.forEach(Thread::start);

        listThreads.forEach(thread -> {
            try {
                thread.join(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (thread.isAlive()) {
                thread.interrupt();
            }
        });

        for (int i = 0; i < inputNumbers.size(); i++) {
            FactorialThread factorialThread = listThreads.get(i);
            if (factorialThread.isFinished) {
                System.out.println("Factorial of " + inputNumbers.get(i) + " is " + factorialThread.getResult());
            } else {
                System.out.println("The calculation for " + inputNumbers.get(i) + " is still in progress");
            }
        }


    }

    public static class FactorialThread extends Thread {

        private final long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            this.result = this.factorial(inputNumber);
            this.isFinished = true;
        }

        public BigInteger factorial(long n) {
            BigInteger tempResult = BigInteger.ONE;
            for (long i = n; i > 0; i--) {
                if (currentThread().isInterrupted()) {
                    System.out.println("The calculation of " + n + " was interrupted");
                    return BigInteger.ZERO;
                }
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }
            return tempResult;
        }

        public boolean isFinished() {
            return this.isFinished;
        }

        public BigInteger getResult() {
            return this.result;
        }

    }

}
