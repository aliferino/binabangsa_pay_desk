import javax.swing.*;
import java.awt.*;

/**
 * Main entry point for the desktop SPP payment application.
 * Creates and shows the main window with a welcome panel and menu bar.
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGui());
    }

    private static void createAndShowGui() {
        JFrame frame = new JFrame("Sistem Pembayaran SPP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setMinimumSize(new Dimension(700, 450));
        frame.setLocationRelativeTo(null);

        // Menu bar
        JMenuBar menuBar = createMenuBar();
        frame.setJMenuBar(menuBar);

        // Welcome panel
        JPanel welcome = new JPanel(new BorderLayout());
        welcome.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));

        JLabel titleLabel = new JLabel("Sistem Pembayaran SPP", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(28f).deriveFont(Font.BOLD));

        JLabel subtitle = new JLabel("Aplikasi Desktop Bina Bangsa", SwingConstants.CENTER);
        subtitle.setFont(subtitle.getFont().deriveFont(16f));
        subtitle.setForeground(Color.GRAY);

        JLabel info = new JLabel(
            "<html><div style='text-align:center;'>"
            + "Metode pembayaran: TUNAI dan QRIS<br>"
            + "Silakan login melalui menu Aplikasi > Login"
            + "</div></html>",
            SwingConstants.CENTER
        );
        info.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.add(titleLabel, BorderLayout.NORTH);
        textPanel.add(subtitle, BorderLayout.CENTER);
        textPanel.add(info, BorderLayout.SOUTH);

        welcome.add(textPanel, BorderLayout.CENTER);
        frame.add(welcome, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private static JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu appMenu = new JMenu("Aplikasi");
        JMenuItem loginItem = new JMenuItem("Login");
        JMenuItem exitItem = new JMenuItem("Keluar");
        loginItem.addActionListener(e -> JOptionPane.showMessageDialog(null,
            "Form login akan tersedia di tahap berikutnya.",
            "Info", JOptionPane.INFORMATION_MESSAGE));
        exitItem.addActionListener(e -> System.exit(0));
        appMenu.add(loginItem);
        appMenu.addSeparator();
        appMenu.add(exitItem);

        JMenu helpMenu = new JMenu("Bantuan");
        JMenuItem aboutItem = new JMenuItem("Tentang");
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(null,
            "binabangsa_pay_desk\nAplikasi Desktop Sistem Pembayaran SPP\nJava 24, Swing, MySQL",
            "Tentang", JOptionPane.INFORMATION_MESSAGE));
        helpMenu.add(aboutItem);

        menuBar.add(appMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }
}
