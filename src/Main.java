import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

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
        JMenuBar menuBar = createMenuBar(frame);
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

    private static JMenuBar createMenuBar(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();

        JMenu appMenu = new JMenu("Aplikasi");
        appMenu.setMnemonic(KeyEvent.VK_A);
        JMenuItem loginItem = new JMenuItem("Login");
        loginItem.addActionListener(e -> {
            LoginView loginView = new LoginView(frame);
            loginView.setVisible(true);
        });
        JMenuItem exitItem = new JMenuItem("Keluar");
        exitItem.addActionListener(e -> System.exit(0));
        appMenu.add(loginItem);
        appMenu.addSeparator();
        appMenu.add(exitItem);

        JMenu adminMenu = new JMenu("Admin");
        adminMenu.setMnemonic(KeyEvent.VK_M);
        addMenu(adminMenu, "Dashboard", e -> switchPanel(frame, new DashboardView()));
        addMenu(adminMenu, "Data Siswa", e -> switchPanel(frame, new StudentView()));
        addMenu(adminMenu, "Data Jurusan", e -> switchPanel(frame, new MajorView()));
        addMenu(adminMenu, "Data Kelas", e -> switchPanel(frame, new ClassroomView()));
        addMenu(adminMenu, "Data Tahun Ajaran", e -> switchPanel(frame, new AcademicYearView()));
        addMenu(adminMenu, "Tarif SPP", e -> switchPanel(frame, new TuitionRateView()));
        addMenu(adminMenu, "Tagihan SPP", e -> switchPanel(frame, new TuitionBillView()));
        addMenu(adminMenu, "Pengguna", e -> switchPanel(frame, new UserView()));

        JMenu helpMenu = new JMenu("Bantuan");
        helpMenu.setMnemonic(KeyEvent.VK_B);
        JMenuItem aboutItem = new JMenuItem("Tentang");
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(frame,
                "binabangsa_pay_desk\nAplikasi Desktop Sistem Pembayaran SPP\nJava 24, Swing, MySQL",
                "Tentang", JOptionPane.INFORMATION_MESSAGE));
        helpMenu.add(aboutItem);

        menuBar.add(appMenu);
        menuBar.add(adminMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }

    private static void addMenu(JMenu menu, String title,
                                java.awt.event.ActionListener listener) {
        JMenuItem item = new JMenuItem(title);
        item.addActionListener(listener);
        menu.add(item);
    }

    private static void switchPanel(JFrame frame, JPanel panel) {
        Container content = frame.getContentPane();
        content.removeAll();
        content.add(panel, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
    }
}
