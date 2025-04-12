package src.fundamentals.cordination.example2;

import java.math.BigInteger;

public class Main2 {

    public static void main(String[] args) {
        Thread thread = new Thread(new LongComputationTask(BigInteger.valueOf(2L), BigInteger.valueOf(10L)));
        thread.setDaemon(true);
        thread.start();
        thread.interrupt();
    }

    private static class LongComputationTask implements Runnable {

        private final BigInteger power;
        private final BigInteger base;

        public LongComputationTask(BigInteger power, BigInteger base) {
            this.power = power;
            this.base = base;
        }

        @Override
        public void run() {
            System.out.println(base + "^" + power + " = " + pow(base, power));
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;
            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }
            return result;
        }
    }
}
