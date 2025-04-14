package dataSharing.reentrantLock;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {

    private static final Map<String, JLabel> cryptoLabels = new HashMap<>();
    private static PricesContainer pricesContainer;

    public static void main(String[] args) {
        // Set FlatLaf modern theme
        FlatLightLaf.setup();

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Cryptocurrency Prices");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            JPanel contentPanel = new JPanel(new GridBagLayout());
            contentPanel.setOpaque(false); // So background animation shows through
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.anchor = GridBagConstraints.WEST;

            // Crypto labels map
            String[] coins = {"BTC", "ETH", "LTC", "BCH", "XRP"};
            for (String coin : coins) {
                cryptoLabels.put(coin, new JLabel("0"));
            }

            // Add labels with mouse interaction
            addLabelsToPanel(contentPanel, gbc);

            // Background panel with animated color
            AnimatedBackgroundPanel backgroundPanel = new AnimatedBackgroundPanel();
            backgroundPanel.setLayout(new GridBagLayout());
            backgroundPanel.add(contentPanel);

            frame.setContentPane(backgroundPanel);
            frame.setVisible(true);

            // Start threads
            pricesContainer = new PricesContainer();
            new PriceUpdater(pricesContainer).start();
            new UIUpdater().start();
        });
    }

    private static void addLabelsToPanel(JPanel panel, GridBagConstraints gbc) {
        int row = 0;
        for (Map.Entry<String, JLabel> entry : ReentrantLockExample.cryptoLabels.entrySet()) {
            String cryptoName = entry.getKey();

            JLabel nameLabel = new JLabel(cryptoName + ": ");
            nameLabel.setForeground(Color.BLUE);

            // Add mouse interaction like in JavaFX
            nameLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    nameLabel.setForeground(Color.RED);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    nameLabel.setForeground(Color.BLUE);
                }
            });

            JLabel valueLabel = entry.getValue();

            gbc.gridx = 0;
            gbc.gridy = row;
            panel.add(nameLabel, gbc);

            gbc.gridx = 1;
            panel.add(valueLabel, gbc);

            row++;
        }
    }

    public static class AnimatedBackgroundPanel extends JPanel {
        private final Color color1 = new Color(144, 238, 144); // Light green
        private final Color color2 = new Color(173, 216, 230); // Light blue
        private float progress = 0.0f;
        private boolean forward = true;

        public AnimatedBackgroundPanel() {
            Timer timer = new Timer(40, e -> {
                if (forward) {
                    progress += 0.01f;
                    if (progress >= 1f) {
                        progress = 1f;
                        forward = false;
                    }
                } else {
                    progress -= 0.01f;
                    if (progress <= 0f) {
                        progress = 0f;
                        forward = true;
                    }
                }
                repaint();
            });
            timer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Color blended = blend(color1, color2, progress);
            g.setColor(blended);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        private Color blend(Color c1, Color c2, float ratio) {
            float inverse = 1.0f - ratio;
            int r = (int)(c1.getRed() * inverse + c2.getRed() * ratio);
            int g = (int)(c1.getGreen() * inverse + c2.getGreen() * ratio);
            int b = (int)(c1.getBlue() * inverse + c2.getBlue() * ratio);
            return new Color(r, g, b);
        }
    }

    // Updates the UI every 500ms using ReentrantLock
    static class UIUpdater extends Thread {
        @Override
        public void run() {
            while (true) {
                if (pricesContainer.getLockObject().tryLock()) {
                    try {
                        SwingUtilities.invokeLater(() -> {
                            cryptoLabels.get("BTC").setText(String.valueOf(pricesContainer.getBitcoinPrice()));
                            cryptoLabels.get("ETH").setText(String.valueOf(pricesContainer.getEtherPrice()));
                            cryptoLabels.get("LTC").setText(String.valueOf(pricesContainer.getLitecoinPrice()));
                            cryptoLabels.get("BCH").setText(String.valueOf(pricesContainer.getBitcoinCashPrice()));
                            cryptoLabels.get("XRP").setText(String.format("%.4f", pricesContainer.getRipplePrice()));
                        });
                    } finally {
                        pricesContainer.getLockObject().unlock();
                    }
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {}
            }
        }
    }

    static class PricesContainer {
        private final Lock lockObject = new ReentrantLock();
        private double bitcoinPrice;
        private double etherPrice;
        private double litecoinPrice;
        private double bitcoinCashPrice;
        private double ripplePrice;

        public Lock getLockObject() { return lockObject; }
        public double getBitcoinPrice() { return bitcoinPrice; }
        public void setBitcoinPrice(double bitcoinPrice) { this.bitcoinPrice = bitcoinPrice; }
        public double getEtherPrice() { return etherPrice; }
        public void setEtherPrice(double etherPrice) { this.etherPrice = etherPrice; }
        public double getLitecoinPrice() { return litecoinPrice; }
        public void setLitecoinPrice(double litecoinPrice) { this.litecoinPrice = litecoinPrice; }
        public double getBitcoinCashPrice() { return bitcoinCashPrice; }
        public void setBitcoinCashPrice(double bitcoinCashPrice) { this.bitcoinCashPrice = bitcoinCashPrice; }
        public double getRipplePrice() { return ripplePrice; }
        public void setRipplePrice(double ripplePrice) { this.ripplePrice = ripplePrice; }
    }

    static class PriceUpdater extends Thread {
        private final PricesContainer container;
        private final Random random = new Random();

        public PriceUpdater(PricesContainer container) {
            this.container = container;
        }

        @Override
        public void run() {
            while (true) {
                container.getLockObject().lock();
                try {
                    container.setBitcoinPrice(random.nextInt(20000));
                    container.setEtherPrice(random.nextInt(2000));
                    container.setLitecoinPrice(random.nextInt(500));
                    container.setBitcoinCashPrice(random.nextInt(5000));
                    container.setRipplePrice(random.nextDouble());
                } finally {
                    container.getLockObject().unlock();
                }

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}
            }
        }
    }
}
